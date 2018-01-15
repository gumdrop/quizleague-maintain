package quizleague.web.model

import scala.scalajs.js
import scala.scalajs.js.annotation.ScalaJSDefined

 class Venue(
    val id:String,
    val name:String,
    val address:String,
    val phone:String,
    val email:String,
    val website:String,
    val imageURL:String,
    val retired:Boolean = false
) extends Model

object Venue{
  def apply(     id:String,
     name:String,
     address:String,
     phone:String,
     email:String,
     website:String,
     imageURL:String,
     retired:Boolean = false) = new Venue(id,name,address,phone,email,website,imageURL,retired)
}
