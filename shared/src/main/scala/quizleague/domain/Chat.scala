package quizleague.domain

import java.net.URL
import java.time.LocalDateTime

case class Chat(
                 id: String,
                 messages: List[ChatMessage],
                 retired: Boolean = false
               ) extends Entity

case class ChatMessage(
                        user: Ref[SiteUser],
                        message: String,
                        date: LocalDateTime
                      )

case class SiteUser(
                     id: String,
                     handle: String,
                     avatar:String,
                     user: Option[Ref[User]],
                     retired: Boolean = false

                   ) extends Entity
