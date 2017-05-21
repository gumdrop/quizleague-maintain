package quizleague.web.model

import angulate2.std._
import scala.scalajs.js.annotation.JSExport

@Data
case class Venue(
    id:String,
    name:String,
    address:String,
    phone:String,
    email:String,
    website:String,
    imageURL:String,
    retired:Boolean = false
)

