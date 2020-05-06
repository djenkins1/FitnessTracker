var path = require('path');

module.exports = (env) => {
  return require(`./webpack.config.${env}.js`)
}
