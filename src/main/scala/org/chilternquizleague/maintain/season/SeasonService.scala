package org.chilternquizleague.maintain.season


import angulate2.std.Injectable
import angulate2.ext.classModeScala
import angulate2.http.Http
import org.chilternquizleague.maintain.service.EntityService
import org.chilternquizleague.maintain.model._
import org.chilternquizleague.maintain.domain.{Season => Dom}
import org.chilternquizleague.maintain.domain.Ref
import rxjs.Observable
import org.chilternquizleague.maintain.component.ComponentNames
import scala.scalajs.js
import org.chilternquizleague.maintain.text.TextService
import java.time.Year
import org.chilternquizleague.util.DateTimeConverters._
import scala.scalajs.js.Date


@Injectable
@classModeScala
class SeasonService(override val http:Http, textService:TextService) extends EntityService[Season] with SeasonNames{
  override type U = Dom

  override protected def mapIn(season:Season) = Dom(season.id, season.startYear, season.endYear, textService.getRef(season.text), List())
  override protected def mapOutSparse(season:Dom) = Season(season.id, season.startYear, season.endYear, null)
  override protected def make() = Dom(newId(), Year.parse(new Date().getFullYear.toString), Year.parse(new Date().getFullYear.toString) plusYears 1, textService.getRef(textService.instance()),List())
  override protected def mapOut(season:Dom) =
    Observable.zip(
        Observable.of(season),
        textService.get(season.text),
        (season:Dom, text:Text) => log(Season(season.id, season.startYear, season.endYear,text)))
  
  override def save(season:Season) = {textService.save(season.text);super.save(season)}
  override def flush() = {textService.flush();super.flush()}
  
  import io.circe._, io.circe.generic.auto._, io.circe.parser._, io.circe.syntax._
  import org.chilternquizleague.util.json.codecs.ScalaTimeCodecs._
  override def ser(item:Dom) = item.asJson.noSpaces
  override def deser(jsonString:String) = decode[Dom](jsonString).merge.asInstanceOf[Dom]
 

}


