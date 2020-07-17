package quizleague.domain

import java.time.LocalDateTime

case class User(
    id:String,
    name:String,
    email:String,
    retired:Boolean = false
) extends Entity

case class SiteUser(
                     id: String,
                     handle: String,
                     avatar:String,
                     user: Option[Ref[User]],
                     uid:Option[String],
                     retired: Boolean = false

 ) extends Entity