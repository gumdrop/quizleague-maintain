package quizleague.web.service.results

import angulate2.std.Injectable
import angulate2.ext.classModeScala
import angulate2.http.Http
import quizleague.web.service.EntityService
import quizleague.web.model._
import quizleague.web.model.{ Result => Model }
import quizleague.domain.{ Result => Dom }
import quizleague.domain.{ Report => DomReport }
import quizleague.domain.Ref
import rxjs.Observable
import scala.scalajs.js
import js.JSConverters._
import js.ArrayOps
import quizleague.web.service._
import java.time.Year
import quizleague.web.util.DateTimeConverters._
import scala.scalajs.js.Date
import quizleague.web.names.ResultsNames
import quizleague.web.names.ResultNames
import quizleague.web.service.user.UserGetService
import quizleague.web.service.fixtures.FixtureGetService
import quizleague.web.service.text.TextGetService
import quizleague.web.service.team.TeamGetService
import quizleague.web.service.user.UserPutService
import quizleague.web.service.fixtures.FixturePutService
import quizleague.web.service.text.TextPutService
import quizleague.web.service.team.TeamPutService
import quizleague.web.service.DirtyListService


trait ResultGetService extends GetService[Model] with ResultNames {
  override type U = Dom
  
  val userService:UserGetService
  val fixtureService:FixtureGetService
  val textService:TextGetService
  val teamService:TeamGetService

  override protected def mapOutSparse(dom: Dom) = Model(dom.id,refObs(dom.fixture, fixtureService),dom.homeScore, dom.awayScore, refObs(dom.submitter, userService), dom.note, !dom.reports.isEmpty,mapReports(dom.reports))

  private def mapReports(reports:List[DomReport]) =  reports.map(r => Report(refObs(r.team, teamService),refObs(r.text, textService))).toJSArray
   
  
  import io.circe._, io.circe.generic.auto._, io.circe.parser._, io.circe.syntax._
  import quizleague.util.json.codecs.ScalaTimeCodecs._
  override def deser(jsonString: String) = decode[Dom](jsonString).merge.asInstanceOf[Dom]

}

trait ResultPutService extends PutService[Model] with ResultGetService with DirtyListService[Model] {
  
  override val userService:UserPutService
  override val fixtureService:FixturePutService
  override val textService:TextPutService
  override val teamService:TeamPutService
  
  override protected def mapIn(model: Model) = Dom(
      model.id, 
      fixtureService.ref(model.fixture),
      model.homeScore, 
      model.awayScore, 
      userService.ref(model.submitter),
      model.note,
      model.reports.map(r => DomReport(teamService.ref(r.team), textService.ref(r.text))).toList
      )

  override protected def make() = ???
  
  override def save(model:Model) = {textService.saveAllDirty; super.save(model)}

  import io.circe._, io.circe.generic.auto._, io.circe.parser._, io.circe.syntax._
  import quizleague.util.json.codecs.ScalaTimeCodecs._
  override def ser(item: Dom) = item.asJson.noSpaces

}