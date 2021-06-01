const express = require("express");
const cors = require("cors");
const fileUpload = require("express-fileupload");
const fs = require("fs");
const exec = require("await-exec");
const ff = require("node-find-folder");
const { resolve } = require("path");
const { v4: uuidv4 } = require("uuid");
const { rejects } = require("assert");
const metrics = require("./projectMetrics");
const projectUtils = require("./projectUtils");

const app = express();
const originalPath = __dirname;

app.use(cors());
app.use(fileUpload());

//Get the proejct metrics themselves
app.use("/metrics", express.static("reports"));

// Get general info about the projects
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

// Upload Endpoint
// TODO obtain token here
app.post("/upload", (req, res) => {
  if (req.files === null) {
    return res.status(400).json({ msg: "No file uploaded" });
  }

  const file = req.files.file;
  const token = req.body.token;
  const fileName = file.name;
  const ext = fileName.split(".")[fileName.split(".").length - 1];
  let project_token = uuidv4().split("-").join("").slice(0, 7);

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
  )
  file.mv(
    `${__dirname}/res/upload-dir/${token}/${project_token}/${file.name}`,
    async (err) => {
      if (err) {
        console.error(err);
        return res.status(500).send(err);
      }
      await projectUtils.decompileUserProject(fileName, token, project_token);
      await projectUtils.generateDependecies(token, project_token);

      let result = await depsFromJar(fileName, token, project_token);

      res.json({
        fileName: file.name,
        filePath: `/upload-dir/${token}/${project_token}/${file.name}`,
        data: result,
      });
    }
  );
});

app.listen(5000, () => console.log("Server Started..."));


const depsFromJar = async (proj_name, token, project_token) => {
  await getDeps(
    `./res/deps/${token}/${project_token}`,
    token,
    project_token,
    proj_name
  ).catch((err) => {
    console.log(err);
  });
  console.log(deps);
  await metrics.parseMetric({
    directory: `./classes/${token}/${project_token}`,
    ruleset: `./ruleset1.xml`,
  }).catch((err)=>{
    console.log(err)
  })
  return deps[`${proj_name}.dot`];
};

let deps = {};

const getDeps = async(path, token, project_token, proj_name) => {
  const dir = await fs.promises.opendir(path);
  for await (const dirent of dir) {
    console.log(dirent)
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
}
