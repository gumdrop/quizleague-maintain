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
import org.chilterquizleague.maintain.fixtures.FixturesService
import org.chilternquizleague.maintain.results.ResultsService

@Injectable
@classModeScala
class CompetitionService(
    override val http: Http,
    textService: TextService,
    resultsService: ResultsService,
    fixturesService: FixturesService) extends EntityService[Competition] with CompetitionNames {
  override type U = Dom
   
  import Helpers._

  override protected def mapIn(comp: Competition) = doMapIn(comp)
  override protected def mapOutSparse(comp: Dom) = ???
  override protected def make() = ???
  override protected def mapOut(comp: Dom) = ???

  import io.circe._, io.circe.generic.auto._, io.circe.parser._, io.circe.syntax._
  import org.chilternquizleague.util.json.codecs.ScalaTimeCodecs._
  
  override def ser(item: Dom) = item.asJson.noSpaces
  override def deser(jsonString: String) = decode[Dom](jsonString).merge.asInstanceOf[Dom]

  object Helpers {
    import org.chilternquizleague.util.DateTimeConverters
    import org.chilternquizleague.maintain.domain
    import domain.{ LeagueCompetition => DLC }
    import domain.{ CupCompetition => DCC }
    import domain.{ SubsidiaryLeagueCompetition => DSC }

    def doMapOutSparse(dom:Dom) = {
      dom match { 
        case c:DLC => LeagueCompetition(
          c.id,
          c.name,
          c.startTime,
          c.duration,
          js.Array(),
          js.Array(),
          js.Array(),
          null,
          null
        )
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
          getRef(l.subsidiary))

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




