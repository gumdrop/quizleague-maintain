package quizleague.web.model

import angulate2.std.Data
import scala.scalajs.js
import quizleague.web.util.rx.RefObservable

@Data
case class Fixtures(
    id:String, 
    description:String,
    parentDescription:String,
    date:String,
    start:String,
    duration:Float,
    fixtures:js.Array[RefObservable[Fixture]])
    
@Data
case class Fixture(
  id:String,
  description:String,
  parentDescription:String,
  venue: RefObservable[Venue],
  home:RefObservable[Team],
  away:RefObservable[Team],
  date: String,
  time: String,
  duration : Float
)