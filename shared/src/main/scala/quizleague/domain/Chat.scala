package quizleague.domain

import java.time.LocalDateTime

case class Chat(
                 id: String,
                 messages: List[ChatMessage],
                 retired: Boolean = false
               ) extends Entity

case class ChatMessage(
                        user: ChatUser,
                        message: String,
                        date: LocalDateTime
                      )

case class ChatUser(
                     id: String,
                     handle: String,
                     user: Option[Ref[User]],
                     retired: Boolean = false

                   ) extends Entity
