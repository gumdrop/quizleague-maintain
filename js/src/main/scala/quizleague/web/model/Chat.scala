package quizleague.web.model

import quizleague.web.util.rx.RefObservable

import scala.scalajs.js

class Chat(
            val id: String,
            val messages: js.Array[ChatMessage],
            val retired: Boolean = false
          ) extends Model

class ChatMessage(
                   val user: RefObservable[SiteUser],
                   val message: String,
                   val date: String
                 )

class SiteUser(
                val id: String,
                val handle: String,
                val avatar: String,
                val user: RefObservable[User],
                val retired: Boolean = false

              ) extends Model
