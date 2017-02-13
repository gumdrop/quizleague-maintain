package org.chilternquizleague.web.service.results

import angulate2.std.Injectable
import angulate2.ext.classModeScala
import angulate2.http.Http
import org.chilternquizleague.web.service.EntityService
import org.chilternquizleague.web.model._
import org.chilternquizleague.web.model.{ Results => Model }
import org.chilternquizleague.domain.{ Results => Dom }
import org.chilternquizleague.domain.Ref
import rxjs.Observable
import scala.scalajs.js
import org.chilternquizleague.web.service._
import java.time.Year
import org.chilternquizleague.web.util.DateTimeConverters._
import scala.scalajs.js.Date
import org.chilternquizleague.web.maintain.results.ResultsNames


trait ResultsGetService extends GetService[Results] with ResultsNames {
  override type U = Dom

  override protected def mapOutSparse(dom: Dom) = ???
  override protected def mapOut(dom: Dom) = ???

  import io.circe._, io.circe.generic.auto._, io.circe.parser._, io.circe.syntax._
  import org.chilternquizleague.util.json.codecs.ScalaTimeCodecs._
  override def deser(jsonString: String) = decode[Dom](jsonString).merge.asInstanceOf[Dom]

}

trait ResultsPutService extends PutService[Results] with ResultsGetService {
  override protected def mapIn(model: Model) = ???

  override protected def make() = ???

  import io.circe._, io.circe.generic.auto._, io.circe.parser._, io.circe.syntax._
  import org.chilternquizleague.util.json.codecs.ScalaTimeCodecs._
  override def ser(item: Dom) = item.asJson.noSpaces

}