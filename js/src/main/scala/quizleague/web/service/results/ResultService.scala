package quizleague.web.service.results

import angulate2.std.Injectable
import angulate2.ext.classModeScala
import angulate2.http.Http
import quizleague.web.service.EntityService
import quizleague.web.model._
import quizleague.web.model.{ Result => Model }
import quizleague.domain.{ Result => Dom }
import quizleague.domain.Ref
import rxjs.Observable
import scala.scalajs.js
import quizleague.web.service._
import java.time.Year
import quizleague.web.util.DateTimeConverters._
import scala.scalajs.js.Date
import quizleague.web.maintain.results.ResultsNames
import quizleague.web.maintain.results.ResultNames


trait ResultGetService extends GetService[Model] with ResultNames {
  override type U = Dom

  override protected def mapOutSparse(dom: Dom) = Model(dom.id,null,dom.homeScore, dom.awayScore, null, List())
  override protected def mapOut(dom: Dom) = ???

  import io.circe._, io.circe.generic.auto._, io.circe.parser._, io.circe.syntax._
  import quizleague.util.json.codecs.ScalaTimeCodecs._
  override def deser(jsonString: String) = decode[Dom](jsonString).merge.asInstanceOf[Dom]

}

trait ResultPutService extends PutService[Model] with ResultGetService {
  override protected def mapIn(model: Model) = ???

  override protected def make() = ???

  import io.circe._, io.circe.generic.auto._, io.circe.parser._, io.circe.syntax._
  import quizleague.util.json.codecs.ScalaTimeCodecs._
  override def ser(item: Dom) = item.asJson.noSpaces

}