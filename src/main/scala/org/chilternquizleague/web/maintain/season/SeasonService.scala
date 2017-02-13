package org.chilternquizleague.web.maintain.season


import angulate2.std.Injectable
import angulate2.ext.classModeScala
import angulate2.http.Http
import org.chilternquizleague.web.service.EntityService
import org.chilternquizleague.web.model._
import org.chilternquizleague.domain.{Season => Dom}
import org.chilternquizleague.domain.Ref
import rxjs.Observable
import org.chilternquizleague.web.maintain.component.ComponentNames
import scala.scalajs.js
import org.chilternquizleague.web.maintain.text.TextService
import java.time.Year
import org.chilternquizleague.web.util.DateTimeConverters._
import scala.scalajs.js.Date
import org.chilternquizleague.web.maintain.competition.CompetitionService


@Injectable
@classModeScala
class SeasonService(override val http:Http, textService:TextService, competitionService:CompetitionService) extends EntityService[Season] with SeasonNames{
  override type U = Dom

  override protected def mapIn(season:Season) = Dom(season.id, season.startYear, season.endYear, textService.getRef(season.text), season.competitions.map(competitionService.getRef(_)).toList)
  override protected def mapOutSparse(season:Dom) = Season(season.id, season.startYear, season.endYear, null, js.Array())
  override protected def make() = Dom(newId(), Year.parse(new Date().getFullYear.toString), Year.parse(new Date().getFullYear.toString) plusYears 1, textService.getRef(textService.instance()),List())
  override protected def mapOut(season:Dom) =
    Observable.zip(
        textService.get(season.text),
        mapOutList(season.competitions, competitionService),
        (text:Text, competitions:js.Array[Competition]) => Season(season.id, season.startYear, season.endYear,text, competitions))
  
  override def save(season:Season) = {textService.save(season.text);super.save(season)}
  override def flush() = {textService.flush();super.flush()}
  
  import io.circe._, io.circe.generic.auto._, io.circe.parser._, io.circe.syntax._
  import org.chilternquizleague.web.util.json.codecs.ScalaTimeCodecs._
  override def ser(item:Dom) = item.asJson.noSpaces
  override def deser(jsonString:String) = decode[Dom](jsonString).merge.asInstanceOf[Dom]
 

}


