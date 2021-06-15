const exec = require("await-exec");
const originalPath = __dirname;

exports.parseMetric = async ({
  directory,
  ruleset,
  user_token,
  project_token,
}) => {
  const promises = ruleset.rules.map((rule) =>
    exec(
      `mkdir -p ${originalPath}/reports/${user_token}/${project_token} && ./project-files/pmd-bin-6.34.0/bin/run.sh pmd -d ${directory} -f json -R ${
        ruleset.rulesetFolder
      }/${rule} -r ./reports/${user_token}/${project_token}/${
        rule.split(".")[0]
      }`
    )
  );
  await Promise.all(promises).catch((err)=>{
    throw err
  })
};
