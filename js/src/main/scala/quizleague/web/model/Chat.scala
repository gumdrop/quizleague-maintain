package quizleague.web.model

import quizleague.web.util.rx.RefObservable
import rxscalajs.Observable

import scala.scalajs.js

class Chat(
            val id: String,
            val name:String,
            val messages: Observable[js.Array[ChatMessage]],
            val retired: Boolean = false,
          ) extends Model

class ChatMessage(
                   val id:String,
                   val user: RefObservable[SiteUser],
                   val message: String,
                   val date: String
                 ) extends Model


