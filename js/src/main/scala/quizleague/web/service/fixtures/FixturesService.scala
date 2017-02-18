package quizleague.web.service.fixtures


import angulate2.std.Injectable
import angulate2.ext.classModeScala
import angulate2.http.Http
import quizleague.web.service.EntityService
import quizleague.web.model._
import quizleague.web.model.{Fixtures => Model}
import quizleague.domain.{Fixtures => Dom}
import quizleague.domain.Ref
import rxjs.Observable
import quizleague.web.maintain.component.ComponentNames
import scala.scalajs.js
import quizleague.web.maintain.text.TextService
import java.time.Year
import quizleague.web.util.DateTimeConverters._
import scala.scalajs.js.Date
import quizleague.web.service._
import quizleague.web.maintain.fixtures.FixturesNames



trait FixturesGetService extends GetService[Fixtures] with FixturesNames{
    override type U = Dom

  override protected def mapOutSparse(dom:Dom) = ???
  override protected def mapOut(dom:Dom) = ???
  
  
  import io.circe._, io.circe.generic.auto._, io.circe.parser._, io.circe.syntax._
  import quizleague.util.json.codecs.ScalaTimeCodecs._
  override def deser(jsonString:String) = decode[Dom](jsonString).merge.asInstanceOf[Dom]
 
}

trait FixturesPutService extends PutService[Fixtures] with FixturesGetService{
    override protected def mapIn(model:Model) = ???
  override protected def make() = ???
  
  
  import io.circe._, io.circe.generic.auto._, io.circe.parser._, io.circe.syntax._
  import quizleague.util.json.codecs.ScalaTimeCodecs._
  override def ser(item:Dom) = item.asJson.noSpaces

}

