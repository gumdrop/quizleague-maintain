package quizleague.web.site.results

import scala.scalajs.js.annotation.JSExport

trait TableUtils {
   @JSExport
   def nameClass(score1:Int, score2:Int) = if(score1 > score2) "winner" else "" 
}