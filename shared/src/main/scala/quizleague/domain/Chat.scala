package quizleague.domain

import java.net.URL
import java.time.LocalDateTime

case class Chat(
                 id: String,
                 retired: Boolean = false
               ) extends Entity

case class ChatMessage(
                        id:String,
                        user: Ref[SiteUser],
                        message: String,
                        date: LocalDateTime,
                        retired: Boolean = false
                      ) extends Entity

case class SiteUser(
                     id: String,
                     handle: String,
                     avatar:String,
                     user: Option[Ref[User]],
                     retired: Boolean = false

                   ) extends Entity
