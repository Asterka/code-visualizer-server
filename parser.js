import config from "./config";
const { exec } = require("child_process");

export function parseMetric({directory, ruleset}){
    exec(
        `${config.dependentModulesDirectoy}/pmd-bin-6.34.0/bin/run.sh pmd -d ${directory} -f text -R ${ruleset} -r reportfile.txt`,
        (err, stdout, stderr) => {
            if (err) {
                console.log(
                  `Failed while parsing the metrics`
                );
              } else {
                
              }
        })
}