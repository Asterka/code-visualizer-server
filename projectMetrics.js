const exec = require('await-exec')

exports.parseMetric = async ({directory, ruleset}) => {
    await exec(
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