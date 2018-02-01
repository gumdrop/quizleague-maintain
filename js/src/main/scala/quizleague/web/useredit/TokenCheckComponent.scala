package quizleague.web.useredit

import quizleague.web.core.RouteComponent
import scalajs.js

object TokenCheckComponent extends RouteComponent {
  
  val template = """<div>Authenticating...</div>"""
  
  override val activated = ((c:facade) => {println("activated");c.$router.push("/team/5715999101812736")}):js.ThisFunction
  
}