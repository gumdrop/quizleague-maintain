package quizleague.web.service.statistics

import quizleague.web.model.Statistics
import quizleague.web.names.CompetitionStatisticsNames
import quizleague.web.service.GetService

import scalajs.js
import js.JSConverters._
import quizleague.web.service._
import quizleague.web.model._
import quizleague.domain.stats.{CompetitionStatistics => Dom}
import quizleague.domain.stats.{ResultEntry => DomResultEntry}
import shapeless._
import quizleague.web.names.StatisticsNames
import io.circe._
import io.circe.parser._
import io.circe.syntax._
import io.circe.scalajs.convertJsToJson
import quizleague.util.json.codecs.DomainCodecs._
import quizleague.web.service.competition._
import quizleague.web.service.team._
import quizleague.web.service.season._

trait CompetitionStatisticsGetService extends GetService[CompetitionStatistics] with CompetitionStatisticsNames  {

  val teamService:TeamGetService
  val seasonService:SeasonGetService
  val competitionService:CompetitionGetService

  override type U = Dom
  override protected def mapOutSparse(s: Dom): CompetitionStatistics =
    new CompetitionStatistics(
      s.id,
      s.competitionName,
      s.results.map(toModel _).toJSArray)

  private def toModel(e:DomResultEntry):ResultEntry = new ResultEntry(
    e.seasonText.getOrElse(null),
    seasonService.refObs(e.season),
    competitionService.refObs(e.competition),
    e.teamText.getOrElse(null),
    teamService.refObs(e.team),
    e.position)

  protected def dec(json:js.Any) = decodeJson[U](json)
}

trait CompetitionStatisticsPutService extends PutService[CompetitionStatistics] with CompetitionStatisticsGetService{
  override val teamService: TeamPutService
  override val seasonService: SeasonPutService
  override val competitionService: CompetitionPutService

  private def toDom(s:ResultEntry):DomResultEntry = new DomResultEntry(
    Option(s.seasonText),
    seasonService.refOption(s.season),
    competitionService.refOption(s.competition),
    Option(s.teamText),
    teamService.refOption(s.team),
    s.position)

  protected def mapIn(s: CompetitionStatistics) = Dom(s.id, s.competitionName, s.results.map(toDom _).toList )
  override protected def make() = withKey(Dom(newId(), "", List()), null)

  override def enc(item: Dom) = item.asJson
}
