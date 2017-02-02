package org.chilternquizleague.maintain.results


import angulate2.std.Injectable
import angulate2.ext.classModeScala
import angulate2.http.Http
import org.chilternquizleague.maintain.service.EntityService
import org.chilternquizleague.maintain.model._
import org.chilternquizleague.maintain.model.{Results => Model}
import org.chilternquizleague.maintain.domain.{Results => Dom}
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
class ResultsService(override val http:Http) extends EntityService[Model] with ResultsNames{
  override type U = Dom

  override protected def mapIn(model:Model) = ???
  override protected def mapOutSparse(dom:Dom) = ???
  override protected def make() = ???
  override protected def mapOut(dom:Dom) = ???
  
  
  import io.circe._, io.circe.generic.auto._, io.circe.parser._, io.circe.syntax._
  import org.chilternquizleague.util.json.codecs.ScalaTimeCodecs._
  override def ser(item:Dom) = item.asJson.noSpaces
  override def deser(jsonString:String) = decode[Dom](jsonString).merge.asInstanceOf[Dom]
 

}

trait ResultsNames extends ComponentNames{
  override val typeName = "results"
}


