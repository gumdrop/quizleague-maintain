package quizleague.domain

import java.net.URL
import java.time.LocalDateTime

case class Chat(
                 id: String,
                 name:Option[String] = None,
                 retired: Boolean = false
               ) extends Entity

case class ChatMessage(
                        id:String,
                        user: Ref[SiteUser],
                        message: String,
                        date: LocalDateTime,
                        retired: Boolean = false
                      ) extends Entity


