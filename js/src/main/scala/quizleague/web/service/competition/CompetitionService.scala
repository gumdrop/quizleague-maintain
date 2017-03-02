package quizleague.web.service.competition

import angulate2.std.Injectable
import angulate2.ext.classModeScala
import angulate2.http.Http
import quizleague.web.service.EntityService
import quizleague.web.model._
import quizleague.domain.{ Competition => Dom }
import quizleague.domain.Ref
import rxjs.Observable
import quizleague.web.maintain.component.ComponentNames
import scala.scalajs.js
import quizleague.web.util.DateTimeConverters._
import quizleague.web.service.fixtures._
import quizleague.web.service.results._
import quizleague.web.model.CompetitionType.CompetitionType
import java.time.LocalTime
import java.time.Duration
import quizleague.web.model.CompetitionType
import java.util.concurrent.TimeUnit
import java.time.temporal.ChronoUnit
import quizleague.web.service._
import quizleague.web.service.text._
import quizleague.web.maintain.competition.CompetitionNames
import quizleague.web.util.Logging

trait CompetitionGetService extends GetService[Competition] with CompetitionNames with Logging {
  override type U = Dom

  val textService: TextGetService
  val resultsService: ResultsGetService
  val fixturesService: FixturesGetService

  import Helpers._
  override protected def mapOutSparse(comp: Dom) = doMapOutSparse(comp)
  override protected def mapOut(comp: Dom)(implicit depth:Int) = doMapOut(comp)

  import io.circe._, io.circe.generic.auto._, io.circe.parser._, io.circe.syntax._

  override def deser(jsonString: String) = decode[Dom](jsonString).merge.asInstanceOf[Dom]

  object Helpers {
    import quizleague.web.util.DateTimeConverters._
    import quizleague.domain
    import domain.{ LeagueCompetition => DLC }
    import domain.{ CupCompetition => DCC }
    import domain.{ SubsidiaryLeagueCompetition => DSC }

    def doMapOut(dom: Dom)(implicit depth:Int): Observable[Competition] = {
      if (dom == null) Observable.of(null)
      dom match {
        case c: DLC => Observable.zip(
          mapOutList(c.fixtures, fixturesService),
          mapOutList(c.results, resultsService),
          child(c.text,textService),
          c.subsidiary.map(x => getSparse(x.id)).getOrElse(Observable.of(null)),
          (fixtures: js.Array[Fixtures], results: js.Array[Results], text: Text, subsidiary: Competition) => {
            new LeagueCompetition(
              c.id,
              c.name,
              c.startTime,
              c.duration,
              fixtures,
              results,
              js.Array(),
              text,
              subsidiary)
          })
        case c: DCC => Observable.zip(
          mapOutList(c.fixtures, fixturesService),
          mapOutList(c.results, resultsService),
          child(c.text,textService),
          (fixtures: js.Array[Fixtures], results: js.Array[Results], text: Text) => (new CupCompetition(
            c.id,
            c.name,
            c.startTime,
            c.duration,
            fixtures,
            results,
            text)))

        case c: DSC => Observable.zip(
          mapOutList(c.results, resultsService),
          child(c.text,textService),
          (results: js.Array[Results], text: Text) => (new SubsidiaryLeagueCompetition(
            c.id,
            c.name,
            results,
            js.Array(),
            text)))

      }

    }

    def doMapOutSparse(dom: Dom):Competition = {
      if (dom == null) return null
      dom match {
        case c: DLC => new LeagueCompetition(
          c.id,
          c.name,
          c.startTime,
          c.duration,
          js.Array(),
          js.Array(),
          js.Array(),
          null,
          null)
        case c: DCC => new CupCompetition(
          c.id,
          c.name,
          c.startTime,
          c.duration,
          js.Array(),
          js.Array(),
          null)
        case c: DSC => new SubsidiaryLeagueCompetition(
          c.id,
          c.name,
          js.Array(),
          js.Array(),
          null)
      }
    }

  }

}

trait CompetitionPutService extends CompetitionGetService with DirtyListService[Competition] {
  import PutHelpers._
  override val textService: TextPutService
  override val resultsService: ResultsPutService
  override val fixturesService: FixturesPutService

  override protected def mapIn(comp: Competition) = doMapIn(comp)
  override protected def make() = ???

  override def save(comp: Dom) = { textService.saveAllDirty; fixturesService.saveAllDirty(); super.save(comp) }

  import io.circe._, io.circe.generic.auto._, io.circe.parser._, io.circe.syntax._

  override def ser(item: Dom) = item.asJson.noSpaces

  def instance[A <: Competition](compType: CompetitionType): A = {
    val comp = compType match {
      case CompetitionType.league => makeLeague
      case CompetitionType.cup => makeCup
      case CompetitionType.subsidiary => makeSubsidiary
    }
    add(comp).asInstanceOf[A]
  }

  object PutHelpers {
    import quizleague.web.util.DateTimeConverters._
    import quizleague.domain
    import domain.{ LeagueCompetition => DLC }
    import domain.{ CupCompetition => DCC }
    import domain.{ SubsidiaryLeagueCompetition => DSC }

    def makeLeague = DLC(
      newId(),
      "League",
      LocalTime.of(20, 30),
      Duration.ofSeconds(5400),
      List(),
      List(),
      List(),
      textService.getRef(textService.instance()),
      None)

    def makeCup = DCC(
      newId(),
      "Cup",
      LocalTime.of(20, 30),
      Duration.ofSeconds(5400),
      List(),
      List(),
      textService.getRef(textService.instance()))

    def makeSubsidiary = DSC(
      newId(),
      "Subsidiary",
      List(),
      List(),
      textService.getRef(textService.instance()))

    def doMapIn(comp: Competition) = {
      comp match {
        case l: LeagueCompetition => DLC(
          l.id,
          l.name,
          l.startTime,
          l.duration,
          l.fixtures.map(fixturesService.getRef(_)).toList,
          l.results.map(resultsService.getRef(_)).toList,
          List(),
          textService.getRef(l.text),
          if (l.subsidiary == null) None else Option(getRef(l.subsidiary)))

        case c: CupCompetition => DCC(
          c.id,
          c.name,
          c.startTime,
          c.duration,
          c.fixtures.map(fixturesService.getRef(_)).toList,
          c.results.map(resultsService.getRef(_)).toList,
          textService.getRef(c.text))

        case s: SubsidiaryLeagueCompetition => DSC(
          s.id,
          s.name,
          s.results.map(resultsService.getRef(_)).toList,
          List(),
          textService.getRef(s.text))
      }

    }

  }
}






