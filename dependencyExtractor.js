const fs = require('fs');
const { exec } = require("child_process");

const originalPath = "~/Desktop/projects/nodejs/code-visualizer-server";

function buildProject(){
    exec(`cd ${originalPath}/res/loadedRepo/jadx-master && ./gradlew dist`, (error, stdout, stderr) => {
        if (error) {
            console.log(`error: ${error.message}`);
            return;
        }
        if (stderr) {
            console.log(`stderr: ${stderr}`);
            return;
        }
        console.log(`stdout: ${stdout}`);
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
        
        exec(`jdeps --dot-output ${originalPath}/res/deps -R ${originalPath}/res/jars/* `, (error, stdout, stderr) => {
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
})
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
      //remove the braces and unneeded elments

      //TODO REGEXP matching
      dep = dep.slice(dep.indexOf("{")+2, dep.indexOf("}")).split('\n');
      dep.forEach(element => {
        //only take care of strings that contain "->" symbol (class dependencies definition)
        if(element.indexOf("->")!=-1){
            splittedTempArray = element.split('"');
            className = splittedTempArray[1];
            //get the last significant element, drop the ";" in the end
            dependsOn = splittedTempArray[splittedTempArray.length - 2];
            deps[dirent.name][className] = dependsOn;
        }

    });
 
    });
  }
}



