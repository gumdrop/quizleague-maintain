package org.chilternquizleague.web.service.fixtures


import angulate2.std.Injectable
import angulate2.ext.classModeScala
import angulate2.http.Http
import org.chilternquizleague.web.service.EntityService
import org.chilternquizleague.web.model._
import org.chilternquizleague.web.model.{Fixtures => Model}
import org.chilternquizleague.domain.{Fixtures => Dom}
import org.chilternquizleague.domain.Ref
import rxjs.Observable
import org.chilternquizleague.web.maintain.component.ComponentNames
import scala.scalajs.js
import org.chilternquizleague.web.maintain.text.TextService
import java.time.Year
import org.chilternquizleague.web.util.DateTimeConverters._
import scala.scalajs.js.Date
import org.chilternquizleague.web.service._
import org.chilternquizleague.web.maintain.fixtures.FixturesNames



trait FixturesGetService extends GetService[Fixtures] with FixturesNames{
    override type U = Dom

  override protected def mapOutSparse(dom:Dom) = ???
  override protected def mapOut(dom:Dom) = ???
  
  
  import io.circe._, io.circe.generic.auto._, io.circe.parser._, io.circe.syntax._
  import org.chilternquizleague.util.json.codecs.ScalaTimeCodecs._
  override def deser(jsonString:String) = decode[Dom](jsonString).merge.asInstanceOf[Dom]
 
}

trait FixturesPutService extends PutService[Fixtures] with FixturesGetService{
    override protected def mapIn(model:Model) = ???
  override protected def make() = ???
  
  
  import io.circe._, io.circe.generic.auto._, io.circe.parser._, io.circe.syntax._
  import org.chilternquizleague.util.json.codecs.ScalaTimeCodecs._
  override def ser(item:Dom) = item.asJson.noSpaces

}

