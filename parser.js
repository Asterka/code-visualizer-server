const { exec } = require("child_process");

exports.parseMetric = ({directory, ruleset}) => {
    exec(
        `./project-files/pmd-bin-6.34.0/bin/run.sh pmd -d ${directory} -f json -R ${ruleset} -r reportfile.txt`,
        (err, stdout, stderr) => {
            if (err) {
                console.log(
                  `Failed while parsing the metrics`,
                  stderr
                );
              } else {
                
              }
    })
}