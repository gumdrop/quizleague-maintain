package quizleague.web.service.competition

import angulate2.std.Injectable
import angulate2.ext.classModeScala
import angulate2.http.Http
import quizleague.web.service.EntityService
import quizleague.web.model._
import quizleague.domain.{ Competition => Dom }
import quizleague.domain.Ref
import quizleague.domain.{Event => DomEvent}
import rxjs.Observable
import quizleague.web.names.ComponentNames
import scala.scalajs.js
import quizleague.web.util.DateTimeConverters._
import quizleague.web.service.fixtures._
import quizleague.web.service.results._
import quizleague.web.service.leaguetable._
import quizleague.web.model.CompetitionType.CompetitionType
import org.threeten.bp.LocalTime
import org.threeten.bp.Duration
import quizleague.web.model.CompetitionType
import java.util.concurrent.TimeUnit
import org.threeten.bp.temporal.ChronoUnit
import quizleague.web.service._
import quizleague.web.service.text._
import quizleague.web.util.Logging
import quizleague.web.service.venue.VenueGetService
import quizleague.web.service.venue.VenuePutService
import org.threeten.bp.LocalDate
import quizleague.web.names.CompetitionNames
  import io.circe._, io.circe.generic.auto._, io.circe.parser._
  import quizleague.util.json.codecs.ScalaTimeCodecs._  
  import quizleague.util.json.codecs.DomainCodecs._  

trait CompetitionGetService extends GetService[Competition] with CompetitionNames with Logging {
  override type U = Dom

  val textService: TextGetService
  val resultsService: ResultsGetService
  val fixturesService: FixturesGetService
  val leagueTableService: LeagueTableGetService
  val venueService: VenueGetService

  import Helpers._
  override protected def mapOutSparse(comp: Dom) = doMapOutSparse(comp)
  override protected def mapOut(comp: Dom)(implicit depth:Int) = doMapOut(comp)

    override protected def dec(json:String) = decode[U](json)
  override protected def decList(json:String) = decode[List[U]](json)

  object Helpers {
    import quizleague.web.util.DateTimeConverters._
    import quizleague.domain
    import domain.{ LeagueCompetition => DLC }
    import domain.{ CupCompetition => DCC }
    import domain.{ SubsidiaryLeagueCompetition => DSC }
    import domain.{ SingletonCompetition => DSiC }  
   

    def doMapOut(dom: Dom)(implicit depth:Int): Observable[Competition] = {
      if (dom == null) Observable.of(null)
      dom match {
        case c: DLC => Observable.zip(
          mapOutList(c.fixtures, fixturesService),
          mapOutList(c.results, resultsService),
          mapOutList(c.tables, leagueTableService),
          child(c.text,textService),
          c.subsidiary.map(x => get(x.id)(0)).getOrElse(Observable.of(null)),
          (fixtures: js.Array[Fixtures], results: js.Array[Results], tables:js.Array[LeagueTable],text: Text, subsidiary: Competition) => {
            new LeagueCompetition(
              c.id,
              c.name,
              c.startTime,
              c.duration,
              fixtures,
              results,
              tables,
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
            text))
            
        )

        case c: DSC => Observable.zip(
          mapOutList(c.results, resultsService),
          mapOutList(c.tables, leagueTableService),
          child(c.text,textService),
          (results: js.Array[Results], tables: js.Array[LeagueTable],text: Text) => (new SubsidiaryLeagueCompetition(
            c.id,
            c.name,
            results,
            tables,
            text)))
            
        case c:DSiC => Observable.zip(
            child(c.text,textService),
            eventOut(c.event),
            (text:Text, event:Event) => new SingletonCompetition(c.id,c.name,text,c.textName,event)
        )

      }

    }
    
    def eventOut(ev:Option[DomEvent]):Observable[Event] = ev.map(e => venueService.get(e.venue.id).map((v,i) => Event(v,e.date, e.time, e.duration))).getOrElse(Observable.of(null))


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
        case c : DSiC => new SingletonCompetition(
          c.id,
          c.name,
          null,
          c.textName,
          null
        )
      }
    }

  }

}

trait CompetitionPutService extends CompetitionGetService with DirtyListService[Competition] {
  import PutHelpers._
  override val textService: TextPutService
  override val resultsService: ResultsPutService
  override val fixturesService: FixturesPutService
  override val leagueTableService:LeagueTablePutService
  override val venueService:VenuePutService

  override protected def mapIn(comp: Competition) = doMapIn(comp)
  override protected def make() = ???

  override def save(comp: Dom) = { textService.saveAllDirty; fixturesService.saveAllDirty; resultsService.saveAllDirty; super.save(comp) }

  import io.circe._, io.circe.generic.auto._, io.circe.parser._, io.circe.syntax._

  override def ser(item: Dom) = item.asJson.noSpaces

  def instance[A <: Competition](compType: CompetitionType): A = {
    val comp = compType match {
      case CompetitionType.league => makeLeague
      case CompetitionType.cup => makeCup
      case CompetitionType.subsidiary => makeSubsidiary
      case CompetitionType.singleton => makeSingleton
    }
    add(comp).asInstanceOf[A]
  }

  object PutHelpers {
    import quizleague.web.util.DateTimeConverters._
    import quizleague.domain
    import domain.{ LeagueCompetition => DLC }
    import domain.{ CupCompetition => DCC }
    import domain.{ SubsidiaryLeagueCompetition => DSC }
    import domain.{ SingletonCompetition => DSiC }      

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
    
    def makeSingleton = DSiC(
      newId(),
      "Singleton",
      Option(DomEvent(null,LocalDate.now, LocalTime.of(20,30), Duration.ofMinutes(90))),
      "",
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
          l.tables.map(leagueTableService.getRef(_)).toList,
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
          s.tables.map(leagueTableService.getRef(_)).toList,
          textService.getRef(s.text))
        
        case s: SingletonCompetition => DSiC(
          s.id,
          s.name,
          eventIn(s.event),
          s.textName,
          textService.getRef(s.text))
      }

    }
    
    def eventIn(event:Event) = if(event == null) None else Option(DomEvent(venueService.getRef(event.venue), event.date, event.time, event.duration))

  }
}






