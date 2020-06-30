package quizleague.web.service.competition

import scala.scalajs.js
import java.time.Duration
import java.time.LocalDate
import java.time.LocalTime

import io.circe.syntax.EncoderOps
import quizleague.domain.{Key,Competition => Dom, Event => DomEvent}
import quizleague.util.json.codecs.DomainCodecs.competitionDecoder
import quizleague.util.json.codecs.DomainCodecs.competitionEncoder
import quizleague.web.model.{Key => ModelKey}
import quizleague.web.model.Competition
import quizleague.web.model.CompetitionType
import quizleague.web.model.CompetitionType.CompetitionType
import quizleague.web.model.CupCompetition
import quizleague.web.model.Event
import quizleague.web.model.LeagueCompetition
import quizleague.web.model.SingletonCompetition
import quizleague.web.model.SubsidiaryLeagueCompetition
import quizleague.web.names.CompetitionNames
import quizleague.web.service.{GetService, PutService}
import quizleague.web.service.fixtures.FixturesGetService
import quizleague.web.service.fixtures.FixturesPutService
import quizleague.web.service.leaguetable.LeagueTableGetService
import quizleague.web.service.leaguetable.LeagueTablePutService
import quizleague.web.service.text.TextGetService
import quizleague.web.service.text.TextPutService
import quizleague.web.service.venue.VenueGetService
import quizleague.web.service.venue.VenuePutService
import quizleague.web.util.Logging 

trait CompetitionGetService extends GetService[Competition] with CompetitionNames with Logging {
  override type U = Dom

  val textService: TextGetService
  val fixturesService: FixturesGetService
  val leagueTableService: LeagueTableGetService
  val venueService: VenueGetService
  val competitionService = this

  import Helpers._
  override protected def mapOutSparse(comp: Dom) = doMapOutSparse(comp)

  override protected def dec(json:js.Any) = decodeJson[U](json)

  
  def listVenues() = venueService.list().map(l => l.map(v => venueService.refObs(v.id)))

  object Helpers {
    import quizleague.domain.{ CupCompetition => DCC }
    import quizleague.domain.{ LeagueCompetition => DLC }
    import quizleague.domain.{ SingletonCompetition => DSiC }
    import quizleague.domain.{ SubsidiaryLeagueCompetition => DSC }
    import quizleague.domain
    import quizleague.web.util.DateTimeConverters._  
   
    def unwrapOption[V](opt:Option[V]):V = opt.fold(null.asInstanceOf[V])(x => x)

    def doMapOutSparse(dom: Dom):Competition = {
      if (dom == null) return null
      dom match {
        case c: DLC => new LeagueCompetition(
          c.id,
          c.name,
          c.startTime,
          c.duration,
          fixturesService.list(c.key),
          leagueTableService.list(dom.key),
          refObs(c.text, textService),
          c.textName,          
          unwrapOption(c.icon))
        case c: DCC => new CupCompetition(
          c.id,
          c.name,
          c.startTime,
          c.duration,
          fixturesService.list(c.key),
          refObs(c.text, textService),
          c.textName,
          unwrapOption(c.icon))
        case c: DSC => new SubsidiaryLeagueCompetition(
          c.id,
          c.name,
          fixturesService.list(c.key),
          leagueTableService.list(dom.key),
          refObs(c.text, textService),
          c.textName,
          unwrapOption(c.icon))
        case c : DSiC => new SingletonCompetition(
          c.id,
          c.name,
          refObs(c.text, textService),
          c.textName,
          c.event.filter(_ != null).fold[Event](Event(null,null, null,0))(e => Event(venueService.refObs(e.venue),e.date,e.time,e.duration)),
          unwrapOption(c.icon)
        )
      }
    }

  }

}

trait CompetitionPutService extends CompetitionGetService with PutService[Competition] {
  import PutHelpers._
  override val textService: TextPutService
  override val fixturesService: FixturesPutService
  override val leagueTableService:LeagueTablePutService
  override val venueService:VenuePutService

  override protected def mapIn(comp: Competition) = doMapIn(comp)
  override protected def make() = ???

  import io.circe._, io.circe.generic.auto._, io.circe.parser._, io.circe.syntax._

  override def enc(item: Dom) = item.asJson

  def instance[A <: Competition](compType: CompetitionType, parentKey:ModelKey): A = {
    log(parentKey,"incoming key")
    log(compType.toString,"comp type")

    val comp = compType match {
      case CompetitionType.league => makeLeague
      case CompetitionType.cup => makeCup
      case CompetitionType.subsidiary => makeSubsidiary
      case CompetitionType.singleton => makeSingleton
    }

    log(comp, "comp" )
    add(comp.withKey(Key(Option(parentKey.key), uriRoot,comp.id))).asInstanceOf[A]
  }

  object PutHelpers {
    import quizleague.domain
    import quizleague.domain.{ CupCompetition => DCC }
    import quizleague.domain.{ LeagueCompetition => DLC }
    import quizleague.domain.{ SingletonCompetition => DSiC }
    import quizleague.domain.{ SubsidiaryLeagueCompetition => DSC }
    import quizleague.web.util.DateTimeConverters._      

    def makeLeague = DLC(
      newId(),
      "League",
      LocalTime.of(20, 30),
      Duration.ofSeconds(5400),
      textService.getRef(textService.instance())
    )

    def makeCup = DCC(
      newId(),
      "Cup",
      LocalTime.of(20, 30),
      Duration.ofSeconds(5400),
      textService.getRef(textService.instance()),
      "cup-comp")

    def makeSubsidiary = DSC(
      newId(),
      "Subsidiary",
      textService.getRef(textService.instance()))
    
    def makeSingleton = DSiC(
      newId(),
      "Singleton",
      Option(DomEvent(None,LocalDate.now, LocalTime.of(20,30), Duration.ofMinutes(90))),
      "",
      textService.getRef(textService.instance()))


    def doMapIn(comp: Competition) = {
      comp match {
        case l: LeagueCompetition => DLC(
          l.id,
          l.name,
          l.startTime,
          l.duration,
          textService.ref(l.text),
          l.textName,
          Option(l.icon))

        case c: CupCompetition => DCC(
          c.id,
          c.name,
          c.startTime,
          c.duration,
          textService.ref(c.text),
          c.textName,
          Option(c.icon))

        case s: SubsidiaryLeagueCompetition => DSC(
          s.id,
          s.name,
          textService.ref(s.text),
          s.textName,
          Option(s.icon))
        
        case s: SingletonCompetition => DSiC(
          s.id,
          s.name,
          if(s.event == null) None else Some(DomEvent(venueService.refOption(s.event.venue), s.event.date, s.event.time, s.event.duration)),
          s.textName,
          textService.ref(s.text),
          Option(s.icon))
      }

    }

  }
}






