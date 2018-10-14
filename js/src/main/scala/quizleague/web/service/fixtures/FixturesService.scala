package quizleague.web.service.fixtures


import quizleague.web.service.EntityService
import quizleague.web.model._
import quizleague.web.model.{Fixtures => Model}
import quizleague.domain.{Fixtures => Dom}
import quizleague.domain.Ref
import quizleague.web.names.ComponentNames
import quizleague.util.collection._
import scala.scalajs.js
import java.time.Year
import quizleague.web.util.DateTimeConverters._
import scala.scalajs.js.Date
import quizleague.web.service._
import java.time.LocalTime
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDate
import quizleague.web.service.DirtyListService
import quizleague.web.names.FixturesNames
import quizleague.web.util.rx._
import io.circe._,io.circe.parser._,io.circe.syntax._
import quizleague.util.json.codecs.DomainCodecs._
import js.JSConverters._



trait FixturesGetService extends GetService[Fixtures] with FixturesNames{
    override type U = Dom
    
  val fixtureService:FixtureGetService

  override protected def mapOutSparse(dom:Dom) = Model(dom.id,dom.description, dom.parentDescription,dom.date, dom.start, dom.duration,refObsList(dom.fixtures, fixtureService), dom.subsidiary.getOrElse(false))
  
  override protected def dec(json:js.Any) = decodeJson[U](json)
 
}

trait FixturesPutService extends PutService[Fixtures] with FixturesGetService with DirtyListService[Model] {
  
  override val fixtureService:FixturePutService
  override protected def mapIn(model:Model) = Dom(model.id, model.description, model.parentDescription, model.date, model.start, model.duration, fixtureService.ref(model.fixtures),Option(model.subsidiary))
  override protected def make() = Dom(newId, "","",LocalDate.now,LocalTime.of(20,30), Duration.ofSeconds(5400),List())
  
  def instance(competition:Competition, fixtures:js.Array[Fixtures]) = {
    
    def findNextDate(c:LeagueCompetition) = {
      (fixtures.sortBy(_.date)(Desc)).headOption.map(x => LocalDate parse(x.date).plusWeeks(1)).getOrElse(dateToLocalDate(new Date(Date.now())))
    }
    
    def weekText = s"Week ${fixtures.length + 1}"
    
    
    add(
    competition match {
      case c:LeagueCompetition => Dom(newId, weekText, c.name, findNextDate(c), c.startTime, c.duration, List())
      case c:CupCompetition => Dom(newId,"",c.name,LocalDate.now,c.startTime,c.duration,List())
      case _ => null
    })
  }
  
  def copy(in:Fixtures, parentDescription:String, fixtures:js.Array[RefObservable[Fixture]], subsidiary:Boolean):Fixtures = {
    println("copy fixtures")
    
    val fx = mapIn(in)
    val dom = Dom(newId,fx.description, parentDescription,fx.date,fx.start,fx.duration,fixtureService.ref(fixtures),Some(subsidiary))
    save(dom)
    mapOutSparse(dom)    
  }

  override def enc(item: Dom) = item.asJson
  
  override def save(item:Dom) = {fixtureService.saveAllDirty;super.save(item)}

}

