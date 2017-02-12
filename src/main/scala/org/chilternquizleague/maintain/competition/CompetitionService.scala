package org.chilternquizleague.maintain.competition

import angulate2.std.Injectable
import angulate2.ext.classModeScala
import angulate2.http.Http
import org.chilternquizleague.maintain.service.EntityService
import org.chilternquizleague.maintain.model._
import org.chilternquizleague.maintain.domain.{ Competition => Dom }
import org.chilternquizleague.maintain.domain.Ref
import rxjs.Observable
import org.chilternquizleague.maintain.component.ComponentNames
import scala.scalajs.js
import org.chilternquizleague.maintain.text.TextService
import org.chilternquizleague.util.DateTimeConverters._
import org.chilternquizleague.maintain.fixtures.FixturesService
import org.chilternquizleague.maintain.results.ResultsService
import org.chilternquizleague.maintain.model.CompetitionType.CompetitionType
import java.time.LocalTime
import java.time.Duration
import org.chilternquizleague.maintain.model.CompetitionType
import java.util.concurrent.TimeUnit
import org.chilternquizleague.maintain.service.DirtyListService
import java.time.temporal.ChronoUnit

@Injectable
@classModeScala
class CompetitionService(
    override val http: Http,
    textService: TextService,
    resultsService: ResultsService,
    fixturesService: FixturesService) extends EntityService[Competition] with DirtyListService[Competition] with CompetitionNames {
  override type U = Dom
   
  import Helpers._

  override protected def mapIn(comp: Competition) = doMapIn(comp)
  override protected def mapOutSparse(comp: Dom) = doMapOutSparse(comp)
  override protected def make() = ???
  override protected def mapOut(comp: Dom) = doMapOut(comp)

  override def save(comp:Competition) = {textService.saveAllDirty;super.save(comp)}

  
  import io.circe._, io.circe.generic.auto._, io.circe.parser._, io.circe.syntax._
  
  override def ser(item: Dom) = item.asJson.noSpaces
  override def deser(jsonString: String) = decode[Dom](jsonString).merge.asInstanceOf[Dom]

  def instance[A <: Competition](compType:CompetitionType):A = {
    val comp = compType match {
      case CompetitionType.league => makeLeague
      case CompetitionType.cup => makeCup
      case CompetitionType.subsidiary => makeSubsidiary
    }
    add(comp).asInstanceOf[A]
  }
  
  object Helpers {
    import org.chilternquizleague.util.DateTimeConverters._
    import org.chilternquizleague.maintain.domain
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
      None
    )
    
    def makeCup = DCC(
      newId(),
      "Cup",
      LocalTime.of(20, 30),
      Duration.ofSeconds(5400),
      List(),
      List(),
      textService.getRef(textService.instance())
    )
    
    def makeSubsidiary = DSC(
      newId(),
      "Subsidiary",
      List(),
      List(),
      textService.getRef(textService.instance())
    )
    
    
    def doMapOut(dom: Dom): Observable[Competition] = {
      dom match {
        case c: DLC => Observable.zip(
          mapOutList(c.fixtures, fixturesService),
          mapOutList(c.results, resultsService),
          textService.get(c.text.id),
          c.subsidiary.map(x => getSparse(x.id)).getOrElse(Observable.of(null)),
          (fixtures: js.Array[Fixtures], results: js.Array[Results], text: Text, subsidiary: Competition) => {new LeagueCompetition(
            c.id,
            c.name,
            c.startTime,
            c.duration,
            fixtures,
            results,
            js.Array(),
            text,
            subsidiary)})
        case c: DCC => Observable.zip(
          mapOutList(c.fixtures, fixturesService),
          mapOutList(c.results, resultsService),
          textService.get(c.text.id),
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
          textService.get(c.text.id),
          (results: js.Array[Results], text: Text) => (new SubsidiaryLeagueCompetition(
            c.id,
            c.name,
            results,
            js.Array(),
            text)))
      
      }

      
    }

    def doMapOutSparse(dom: Dom) = {
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
          if(l.subsidiary == null) None else Option(getRef(l.subsidiary))
        )

        case c: CupCompetition => DCC(
          c.id,
          c.name,
          c.startTime,
          c.duration,
          c.fixtures.map(fixturesService.getRef(_)).toList,
          c.results.map(resultsService.getRef(_)).toList,
          textService.getRef(c.text)
        )

        case s: SubsidiaryLeagueCompetition => DSC(
          s.id,
          s.name,
          s.results.map(resultsService.getRef(_)).toList,
          List(),
          textService.getRef(s.text)
        )
      }

    }

  }
}




