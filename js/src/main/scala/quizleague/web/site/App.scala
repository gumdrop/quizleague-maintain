package quizleague.web.site

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport
import scala.scalajs.js.JSApp

object Main extends JSApp {
def main(): Unit = {
  new AppFacade
}}


@js.native
@JSImport("../quizleague-js-sjsx.js",JSImport.Namespace)
class AppFacade extends js.Object