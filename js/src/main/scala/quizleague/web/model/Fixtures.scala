package quizleague.web.model

import angulate2.std.Data
import scala.scalajs.js
import scala.scalajs.js.Date

@Data
case class Fixtures(
    id:String, 
    description:String,
    parentDescription:String,
    date:String,
    start:String,
    duration:Float,
    fixtures:js.Array[Fixture])
    
@Data
case class Fixture(
  id:String,
  description:String,
  parentDescription:String,
  venue: Venue,
  home:Team,
  away:Team,
  date: String,
  time: String,
  duration : Float
)