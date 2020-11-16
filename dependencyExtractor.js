const fs = require('fs');
const { exec } = require('child_process');
const ff = require('node-find-folder');

const proj_name = "PC-Remote.jar";
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
    exec(`sudo rm ${originalPath}/res/deps/*` ,(err, stdout, stderr)=>{
        
        exec(`jdeps --dot-output ${originalPath}/res/deps -R ${originalPath}/res/jars/* && rm ${originalPath}/res/deps/summary.dot`, (error, stdout, stderr) => {
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
        }, 2000)//Magic number
    })
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
                const packageAddress = relPath + packageName.split('.').join('/');
                if (!fs.existsSync(packageAddress)){
                    console.log("no dir ",packageAddress);
                    return;
                }

                //Find all .java class files in the PACKAGE 
                let files = fs.readdirSync(packageAddress).filter((file)=>{
                    if(file.indexOf('.java') === file.length-5){
                        return file;
                    }
                });
                console.log(files);
                const inClasses = [];
                
                files.forEach((file)=>{
                    fs.readFile(file, function(err,data){
                        if (!err) {
                           //console.log(file);
                        } else {
                            //console.log(err);
                        }
                    });    
                })
                
                deps[dirent.name][packageName].push({dependsOn:dependsOn, inClasses: inClasses});
            }
            else{
                deps[dirent.name][packageName] = []
            }
        }

    });
 
    });
  }
}



