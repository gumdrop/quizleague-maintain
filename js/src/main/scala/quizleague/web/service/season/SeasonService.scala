package quizleague.web.service.season

import angulate2.std.Injectable
import angulate2.ext.classModeScala
import angulate2.http.Http
import quizleague.web.service.EntityService
import quizleague.web.model._
import quizleague.domain.{ Season => Dom }
import quizleague.domain.Ref
import rxjs.Observable
import quizleague.web.maintain.component.ComponentNames
import scala.scalajs.js
import quizleague.web.maintain.text.TextService
import java.time.Year
import quizleague.web.util.DateTimeConverters._
import scala.scalajs.js.Date
import quizleague.web.service._
import quizleague.web.service.text._
import quizleague.web.service.competition._
import quizleague.web.maintain.season.SeasonNames

trait SeasonGetService extends GetService[Season] with SeasonNames {
  override type U = Dom
  val textService: TextGetService
  val competitionService: CompetitionGetService

  override protected def mapOutSparse(season: Dom) = Season(season.id, season.startYear, season.endYear, null, js.Array())
  override protected def mapOut(season: Dom) =
    Observable.zip(
      textService.get(season.text),
      mapOutList(season.competitions, competitionService),
      (text: Text, competitions: js.Array[Competition]) => Season(season.id, season.startYear, season.endYear, text, competitions))

  override def flush() = { textService.flush(); super.flush() }

  import io.circe._, io.circe.generic.auto._, io.circe.parser._, io.circe.syntax._
  import quizleague.util.json.codecs.ScalaTimeCodecs._
  override def deser(jsonString: String) = decode[Dom](jsonString).merge.asInstanceOf[Dom]

}

trait SeasonPutService extends PutService[Season] with SeasonGetService {
  override val textService: TextPutService
  override val competitionService: CompetitionPutService

  override protected def mapIn(season: Season) = Dom(season.id, season.startYear, season.endYear, textService.getRef(season.text), season.competitions.map(competitionService.getRef(_)).toList)
  override protected def make() = Dom(newId(), Year.parse(new Date().getFullYear.toString), Year.parse(new Date().getFullYear.toString) plusYears 1, textService.getRef(textService.instance()), List())

  override def save(season: Season) = { textService.save(season.text); super.save(season) }
  override def flush() = { textService.flush(); super.flush() }

  import io.circe._, io.circe.generic.auto._, io.circe.parser._, io.circe.syntax._
  import quizleague.util.json.codecs.ScalaTimeCodecs._
  override def ser(item: Dom) = item.asJson.noSpaces

}




