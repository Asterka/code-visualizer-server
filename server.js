const express = require("express");
const cors = require("cors");
const fileUpload = require("express-fileupload");
const fs = require("fs");
const exec = require("await-exec");
const ff = require("node-find-folder");
const { v4: uuidv4 } = require("uuid");
const metrics = require("./projectMetrics");
const projectUtils = require("./projectUtils");
const { stderr } = require("process");

const app = express();
const originalPath = __dirname;

app.use(cors());
app.use(fileUpload());

//Get the proejct metrics themselves
app.use("/metrics", express.static("reports"));

// Get general info about the projects
const measures = fs.readFileSync(`${__dirname}/res/measures.xml`, "utf-8")


function findMetricByClass(classMetric, parsedXMLMetrics, obj){
	parsedXMLMetrics["METRICS"]["METRIC"].forEach(element => {
		if(element["$"].abbreviation === classMetric){
			obj = { "name":  element["$"].name}
			obj.values = []
			element["VALUE"].forEach(value => {
				let len = obj.values.length
				obj.values[len] = {}
				obj.values[len] = {"className":value["$"]["measured"], "value": value["$"]["value"]}
			});
		}
	});
	return obj;
}

app.get('/api', function(req, res) {
	console.log("Got a request")
	//Get the requested metric name
	const classOfMetric = req.query.metric;
	var response = "";
	let obj = {}
	const result = parseString(measures, function(err, result){
		obj = findMetricByClass(classOfMetric, result, obj);
	})
	res.status(200).json(obj) 
})

app.get("/projects", (req, res) => {
  let user_token = req.query.token;

  const path = `${originalPath}/res/deps/${user_token}`;
  let result_folders = [];

  fs.readdir(path, (err, folders) => {
    if (folders !== undefined) {
      folders.forEach((project) => {
        result_folders.push(project);
      });
      res.json({ projects: result_folders });
    } else {
      res.json({ err: "You have no uploaded projects" });
    }
  });
});

/*POST endpoint to upload the files to the server( Also executes parsing module methods)*/
app.post("/upload", (req, res) => {
  if (req.files === null) {
    return res.status(400).json({ msg: "No file uploaded" });
  }
  /* Get upcoming file, userToken, and filename from the users POST request*/
  const file = req.files.file;
  const token = req.body.token;
  const fileName = file.name;
  let project_token = uuidv4().split("-").join("").slice(0, 7);
  let result = [];
  /* Make a new directory for this project */
  exec(
    `mkdir -p ${originalPath}/res/upload-dir/${token}/${project_token}`,
    (error, stdout, stderr) => {
      if (error) {
        console.log(`error: ${error}`);
        return;
      }
      if (stderr) {
        console.log(`stderr: ${stderr}`);
        return;
      }
    }
  );

  /* With the new file, move it into it's upload directory from temp directory */
  file.mv(
    `${__dirname}/res/upload-dir/${token}/${project_token}/${file.name}`,
    /* Call the asyncronous function that'd return a Promise and then send client data when promise is resolved */
    (async (err) => {
      if (err) {
        console.error(err);
        return res.status(500).send(err);
      }
      /* If no error stopped this from executing, start calling asunchronous functions one by one */
      await projectUtils.decompileUserProject(fileName, token, project_token);
      /* After all the files have been parsed, generate dependencies files between classes using JDEPS */
      await projectUtils.generateDependecies(token, project_token)
      /* After that, execute the dependency parser, and metric parser and form the get responce to the client */
      result = await dependencies(fileName, token, project_token);
    })().then(() => {
      /* Send this POST request's respone to the client side */
      res.json({
        /* The file that has been uploaded for the client to get it */
        fileName: file.name,
        /* Its uploaded path */
        filePath: `/upload-dir/${token}/${project_token}/${file.name}`,
        /* Data that's been retrieved from the project, considering connections between classes*/
        data: result,
      });
    }).catch((error)=>{
      res.status(500).json({
        fileName: ``,
        filePath: ``,
        data: error.stderr,
      });
    })
  );
});

app.listen(5000, () => console.log("Server Started..."));

const dependencies = async (proj_name, token, project_token) => {
  await getDeps(
    `./res/deps/${token}/${project_token}`,
    token,
    project_token,
    proj_name
  ).catch((err) => {
    console.log(err);
  });
  await metrics
    .parseMetric({
      directory: `./classes/${token}/${project_token}`,
      ruleset: [`LOC.xml`, 'CYCOMP.xml', 'CBO.xml'],
      user_token: token,
      project_token: project_token
    })
    .catch((err) => {
      console.log(err);
    });
  return deps[`${proj_name}.dot`];
};

let deps = {};

const getDeps = async (path, token, project_token, proj_name) => {
  const dir = await fs.promises.opendir(path);
  for await (const dirent of dir) {
    console.log(dirent);
    fs.readFile(
      __dirname + `/res/deps/${token}/${project_token}/${dirent.name}`,
      function (err, data) {
        if (err) {
          throw err;
        }
        //All deps
        deps[dirent.name] = {};
        let dep = data.toString();
        //TODO REGEXP matching
        dep = dep.slice(dep.indexOf("{") + 2, dep.indexOf("}")).split("\n");

        dep.forEach((element) => {
          //only take care of strings that contain "->" symbol (class dependencies definition)

          if (element.indexOf("->") != -1) {
            splittedTempArray = element.split('"');
            packageName = splittedTempArray[1];
            const relPath =
              __dirname + `/classes/${token}/${project_token}/${proj_name}/`;
            //get the last significant element, drop the ";" in the end
            dependsOn = splittedTempArray[splittedTempArray.length - 2];
            dependsOn = dependsOn.split(" ")[0];
            //if there is such a PACKAGE array
            if (Object.keys(deps[dirent.name]).indexOf(packageName) != -1) {
              //Look for all the class files of the target package to see if it depends on them
              let file = packageName.split(".");
              file = file.slice(0, -1);
              file = relPath + file.join("/") + ".java";

              const inClasses = [];
              let dependentClasses = [];

              fs.readFile(file, function (err, data) {
                if (!err) {
                  if (data.indexOf(dependsOn) != -1) {
                    dependentClasses.push(file);
                  }
                } else {
                  //console.log(err);
                }
              });

              deps[dirent.name][packageName].push({ dependsOn: dependsOn });
            } else {
              deps[dirent.name][packageName] = [];
            }
          }
        });
      }
    );
  }
};
