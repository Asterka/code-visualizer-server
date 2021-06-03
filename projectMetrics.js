const exec = require("await-exec");
const originalPath = __dirname;

exports.parseMetric = async ({
  directory,
  ruleset,
  user_token,
  project_token,
}) => {
  for await (let rule of ruleset) {
    exec(
      `mkdir -p ${originalPath}/reports/${user_token}/${project_token} && ./project-files/pmd-bin-6.34.0/bin/run.sh pmd -d ${directory} -f json -R ${rule} -r ./reports/${user_token}/${project_token}/${
        rule.split(".")[0]
      }`,
      (err, stdout, stderr) => {
        if (err) {
          console.log(`Failed while parsing the metrics`, stderr);
        } else {
        }
      }
    );
  }
};
