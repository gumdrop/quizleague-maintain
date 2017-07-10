angulate2ScalaJsBundler = require("./full-opt-jsbundler");
module.exports = require('./scalajs.webpack.config');
module.exports.context = __dirname;
angulate2ScalaJsBundler.apply("quizleague-js", module);