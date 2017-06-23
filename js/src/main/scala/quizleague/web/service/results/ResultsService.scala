package quizleague.web.service.results


import quizleague.web.model._
import quizleague.web.model.{ Results => Model }
import quizleague.domain.{ Results => Dom }
import quizleague.domain.Ref
import rxjs.Observable
import scala.scalajs.js
import js.JSConverters._
import js.ArrayOps
import quizleague.web.service._
import java.time.Year
import quizleague.web.util.DateTimeConverters._
import scala.scalajs.js.Date
import quizleague.web.names.ResultsNames
import quizleague.web.service.fixtures.FixturesGetService
import quizleague.web.service.fixtures.FixturesPutService


trait ResultsGetService extends GetService[Results] with ResultsNames {
  override type U = Dom
  val resultService:ResultGetService
  val fixturesService:FixturesGetService

  override protected def mapOutSparse(dom: Dom) = Model(dom.id,refObs(dom.fixtures, fixturesService),refObsList(dom.results, resultService))

  import io.circe._, io.circe.generic.auto._, io.circe.parser._, io.circe.syntax._
  import quizleague.util.json.codecs.ScalaTimeCodecs._
  override def deser(jsonString: String) = decode[Dom](jsonString).merge.asInstanceOf[Dom]

}

trait ResultsPutService extends PutService[Results] with ResultsGetService with DirtyListService[Model]{
  
  override val resultService:ResultPutService
  override val fixturesService:FixturesPutService

  override protected def mapIn(model: Model) = Dom(model.id, fixturesService.ref(model.fixtures), resultService.ref(model.results))

  override def save(model:Model) = {resultService.saveAllDirty;super.save(model)}
  
  override protected def make() = ???
  
  def instance(comp:Competition) = ???

  import io.circe._, io.circe.generic.auto._, io.circe.parser._, io.circe.syntax._
  import quizleague.util.json.codecs.ScalaTimeCodecs._
  override def ser(item: Dom) = item.asJson.noSpaces

}