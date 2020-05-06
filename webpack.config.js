var path = require('path');

module.exports = (env) => {
  var envName = ( env === 'prod' || env === 'test' || env === 'dev' ? env : 'dev' );//default env to dev if not passed in as environment variable
  return require(`./webpack.config.${envName}.js`)
}
