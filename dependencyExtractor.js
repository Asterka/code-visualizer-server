const fs = require('fs');
const { exec } = require('child_process');
const ff = require('node-find-folder');

const proj_name = "org.eclipse.ui.workbench_3.120.0.v20200829-1411.jar";
const originalPath = __dirname;

function buildProject(){
    exec(`cd ${originalPath}/res/loadedRepo/jadx-master && gradle wrapper --gradle-version=6.7 && ./gradlew dist`, (error, stdout, stderr) => {
        if (error) {
            console.log(`error: ${error.message}`);
            return;
        }
        if (stderr) {
            console.log(`stderr: ${stderr}`);
            return;
        }
        //console.log(`stdout: ${stdout}`);
        exec(`jdeps --dot-output ${originalPath}/res/deps ${originalPath}/res/loadedRepo/jadx-master/build/jadx/lib/* `, (error, stdout, stderr) => {
            if (error) {
                console.log(`error: ${error.message}`);
                return;
            }
            if (stderr) {
                console.log(`stderr: ${stderr}`);
                return;
            }
            getDeps(`./res/deps`).catch((err)=>{console.log(err)})
            setTimeout(()=>{
                console.log(deps);
            }, 2000)
        })
    });
}

function depsFromJar(){
    exec(`sudo rm ${originalPath}/res/deps/* && java -jar ./project_files/jd-cmd/jd-cli.jar ./res/jars/${proj_name} -ods ./classes` ,(err, stdout, stderr)=>{
        if(err){
            console.log(`Failed while removing a directory and parsing classes from a jar:\n\n${err.message}`);
        }
        else{
            exec(`jdeps --dot-output ${originalPath}/res/deps -verbose:class -R -filter:none ${originalPath}/res/jars/* && rm ${originalPath}/res/deps/summary.dot`, (error, stdout, stderr) => {
            if (error) {
                console.log(`error: ${error.message}`);
                return;
            }
            if (stderr) {
                console.log(`stderr: ${stderr}`);
                return;
            }
            getDeps(`./res/deps`).catch((err)=>{console.log(err)})
            setTimeout(()=>{
                console.log(deps[`${proj_name}.dot`]);//take the package name here
            }, 1000)//Magic number
        })

}
})

}

function findFolder(name){
        const res = new ff(name.split('.').join('/'), {
            nottraversal: ['node_modules', 'project_files', 'res']//Do not look into unnecessary folders
          })[0];
        return res;
    }

//TODO replace the variable part


depsFromJar();

let deps = {};

async function getDeps(path) {
  const dir = await fs.promises.opendir(path);
  for await (const dirent of dir) {
    fs.readFile( __dirname + `/res/deps/${dirent.name}`, function (err, data) {
      if (err) {
        throw err; 
      }
      //All deps
      deps[dirent.name] = {}
      let dep = data.toString()
      //TODO REGEXP matching
      dep = dep.slice(dep.indexOf("{")+2, dep.indexOf("}")).split('\n');

      dep.forEach(element => {
        //only take care of strings that contain "->" symbol (class dependencies definition)
        
        if(element.indexOf("->")!=-1){
            splittedTempArray = element.split('"');
            packageName = splittedTempArray[1];
            const relPath = __dirname + `/classes/${proj_name}/`;
            //get the last significant element, drop the ";" in the end
            dependsOn = splittedTempArray[splittedTempArray.length - 2];
            dependsOn = dependsOn.split(' ')[0]
            //if there is such a PACKAGE array
            if(Object.keys(deps[dirent.name]).indexOf(packageName) != -1){
                //Look for all the class files of the target package to see if it depends on them
                let file = packageName.split('.')
                file = file.slice(0, -1);
                file = relPath + file.join('/') + '.java';

                // //In case a file is not
                // if (!fs.existsSync(packageAddress)){
                //     console.log("no dir ",packageAddress);
                //     return;
                // }

                // //Find all .java class files in the PACKAGE 
                // let files = fs.readdirSync(packageAddress).filter((file)=>{
                //     if(file.indexOf('.java') === file.length-5){
                //         return file;
                //     }
                // });
                // files = files.map((file)=>{return packageAddress+ '/' + file});
                // // /console.log(files);
                const inClasses = [];
                let dependentClasses = []

                //files.forEach((file)=>{
                    fs.readFile(file, function(err,data){
                        if (!err) {
                           if(data.indexOf(dependsOn) != -1){
                                dependentClasses.push(file);
                           }
                        } else {
                            //console.log(err);
                        }
                    });    
                //})
                
                deps[dirent.name][packageName].push({dependsOn:dependsOn});
            }
            else{
                deps[dirent.name][packageName] = []
            }
        }

    });
 
    });
  }
}



