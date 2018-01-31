package quizleague.web.maintain.text


import scalajs.js

import quizleague.web.service.text._

import quizleague.web.util.UUID
import quizleague.web.names.ComponentNames



import quizleague.web.maintain._
import quizleague.web.core._
import com.felstar.scalajs.vue._


object TextModule extends Module{
  override val routes = @@(
       RouteConfig(path="/maintain/text/:id", components = Map("default" -> TextComponent))
       )
      
}


object TextService extends TextGetService with TextPutService

