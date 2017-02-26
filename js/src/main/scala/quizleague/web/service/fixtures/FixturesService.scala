package quizleague.web.service.fixtures


import angulate2.std.Injectable
import angulate2.ext.classModeScala
import angulate2.http.Http
import quizleague.web.service.EntityService
import quizleague.web.model._
import quizleague.web.model.{Fixtures => Model}
import quizleague.domain.{Fixtures => Dom}
import quizleague.domain.Ref
import rxjs.Observable
import quizleague.web.maintain.component.ComponentNames
import scala.scalajs.js
import quizleague.web.maintain.text.TextService
import java.time.Year
import quizleague.web.util.DateTimeConverters._
import scala.scalajs.js.Date
import quizleague.web.service._
import quizleague.web.maintain.fixtures.FixturesNames
import java.time.LocalTime
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDate
import quizleague.web.service.DirtyListService



trait FixturesGetService extends GetService[Fixtures] with FixturesNames{
    override type U = Dom
    
  val fixtureService:FixtureGetService

  override protected def mapOutSparse(dom:Dom) = Model(dom.id,dom.description, dom.parentDescription,dom.date, dom.start, dom.duration,js.Array())
  override protected def mapOut(dom:Dom) = mapOutList(dom.fixtures,fixtureService,false).
    map((fixtures,i) => Model(dom.id,dom.description, dom.parentDescription,dom.date, dom.start, dom.duration,fixtures))
  
  
  import io.circe._, io.circe.generic.auto._, io.circe.parser._, io.circe.syntax._
  import quizleague.util.json.codecs.ScalaTimeCodecs._
  override def deser(jsonString:String) = decode[Dom](jsonString).merge.asInstanceOf[Dom]
 
}

trait FixturesPutService extends PutService[Fixtures] with FixturesGetService with DirtyListService[Model] {
  
  override val fixtureService:FixturePutService
  override protected def mapIn(model:Model) = Dom(model.id, model.description, model.parentDescription, model.date, model.start, model.duration, model.fixtures.map(fixtureService.getRef(_)).toList)
  override protected def make() = Dom(newId, "","",new Date(Date.now()),LocalTime.of(20,30), Duration.ofSeconds(5400),List())
  
  def instance(competition:Competition) = {
    
    def findNextDate(c:LeagueCompetition) = {
      c.fixtures.sort((a:Model,b:Model) => b.date compareTo a.date).headOption.map(x => LocalDate parse(x.date).plusWeeks(1)).getOrElse(dateToLocalDate(new Date(Date.now())))
    }
    
    add(
    competition match {
      case c:LeagueCompetition => Dom(newId, "", c.name, findNextDate(c), c.startTime, c.duration, List())
    })
  }
  override def save(item:Dom) = {fixtureService.saveAllDirty;super.save(item)}
  
  import io.circe._, io.circe.generic.auto._, io.circe.parser._, io.circe.syntax._
  import quizleague.util.json.codecs.ScalaTimeCodecs._
  override def ser(item:Dom) = item.asJson.noSpaces

}

