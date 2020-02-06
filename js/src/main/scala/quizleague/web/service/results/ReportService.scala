package quizleague.web.service.results

import io.circe.syntax._
import quizleague.domain.{Report => Dom}
import quizleague.util.json.codecs.DomainCodecs._
import quizleague.web.model.{Report => Model}
import quizleague.web.names.ReportNames
import quizleague.web.service.{DirtyListService, _}
import quizleague.web.service.team.{TeamGetService, TeamPutService}
import quizleague.web.service.text.{TextGetService, TextPutService}

import scala.scalajs.js


trait ReportGetService extends GetService[Model] with ReportNames {
  override type U = Dom
  
  val textService:TextGetService
  val teamService:TeamGetService

  override protected def mapOutSparse(dom: Dom) = Model(
    refObs(dom.team, teamService),
    refObs(dom.text, textService))

  override protected def dec(json:js.Any) = decodeJson[U](json)

}

trait ReportPutService extends PutService[Model] with ReportGetService with DirtyListService[Model] {
  
  override val textService:TextPutService
  override val teamService:TeamPutService

  override protected def mapIn(model: Model) = Dom(
    teamService.ref(model.team),
    textService.ref(model.text))

  override protected def make() = ???
  
  override def enc(item: Dom) = item.asJson

}
