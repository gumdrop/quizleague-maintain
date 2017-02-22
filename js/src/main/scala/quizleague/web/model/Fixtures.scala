package quizleague.web.model

import angulate2.std.Data
import scala.scalajs.js
import scala.scalajs.js.Date

@Data
case class Fixtures(
    id:String, 
    description:String,
    parentDescription:String,
    start:String,
    duration:Float,
    fixtures:js.Array[Fixture])
    
@Data
case class Fixture(
  id:String, 
  venue: Venue,
  date: Date,
  time: String,
  duration : Float
)