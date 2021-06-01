const exec = require("await-exec");

exports.decompileUserProject = async (proj_name, token, project_token) => {
  await exec(
    `java -jar ./project-files/jd-cmd/jd-cli.jar ./res/upload-dir/${token}/${project_token}/${proj_name} -ods ./classes/${token}/${project_token} && cd ${__dirname}`
  );
};
exports.generateDependecies = async (token, project_token) => {
  await exec(
    `jdeps --dot-output ${__dirname}/res/deps/${token}/${project_token} -verbose:class -R -filter:none ${__dirname}/res/upload-dir/${token}/${project_token}/* && rm ${__dirname}/res/deps/${token}/${project_token}/summary.dot`,
  );
};
