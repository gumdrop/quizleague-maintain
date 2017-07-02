package quizleague.web.maintain.component

import scala.scalajs.js.annotation.ScalaJSDefined
import scala.scalajs.js

@ScalaJSDefined
object ComponentFnUtils extends js.Object{
  
  def compareWith(s1:js.UndefOr[js.Dynamic], s2:js.UndefOr[js.Dynamic]) = s1 == s2 || ((s1 != null && s2 != null) && (s1.isDefined && s2.isDefined) && s1.get.id == s2.get.id)
  
}