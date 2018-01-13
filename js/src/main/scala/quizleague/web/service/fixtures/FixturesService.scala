package quizleague.web.service.fixtures


import quizleague.web.service.EntityService
import quizleague.web.model._
import quizleague.web.model.{Fixtures => Model}
import quizleague.domain.{Fixtures => Dom}
import quizleague.domain.Ref
import quizleague.web.names.ComponentNames
import quizleague.util.collection._
import scala.scalajs.js
import org.threeten.bp.Year
import quizleague.web.util.DateTimeConverters._
import scala.scalajs.js.Date
import quizleague.web.service._
import org.threeten.bp.LocalTime
import org.threeten.bp.Duration
import org.threeten.bp.LocalDate
import org.threeten.bp.LocalDate
import quizleague.web.service.DirtyListService
import quizleague.web.names.FixturesNames
import quizleague.web.util.rx._
import io.circe._,io.circe.parser._,io.circe.syntax._
import quizleague.util.json.codecs.DomainCodecs._
import js.JSConverters._



trait FixturesGetService extends GetService[Fixtures] with FixturesNames{
    override type U = Dom
    
  val fixtureService:FixtureGetService

  override protected def mapOutSparse(dom:Dom) = Model(dom.id,dom.description, dom.parentDescription,dom.date, dom.start, dom.duration,refObsList(dom.fixtures, fixtureService))
  
  override protected def dec(json:js.Any) = decodeJson[U](json)
 
}

trait FixturesPutService extends PutService[Fixtures] with FixturesGetService with DirtyListService[Model] {
  
  override val fixtureService:FixturePutService
  override protected def mapIn(model:Model) = Dom(model.id, model.description, model.parentDescription, model.date, model.start, model.duration, fixtureService.ref(model.fixtures))
  override protected def make() = Dom(newId, "","",LocalDate.now,LocalTime.of(20,30), Duration.ofSeconds(5400),List())
  
  def instance(competition:Competition, fixtures:js.Array[Fixtures]) = {
    
    def findNextDate(c:LeagueCompetition) = {
      (fixtures.sortBy(_.date)(Desc)).headOption.map(x => LocalDate parse(x.date).plusWeeks(1)).getOrElse(dateToLocalDate(new Date(Date.now())))
    }
    
    add(
    competition match {
      case c:LeagueCompetition => Dom(newId, "", c.name, findNextDate(c), c.startTime, c.duration, List())
      case c:CupCompetition => Dom(newId,"",c.name,LocalDate.now,c.startTime,c.duration,List())
      case _ => null
    })
  }

  override def enc(item: Dom) = item.asJson
  
  override def save(item:Dom) = {fixtureService.saveAllDirty;super.save(item)}

}

