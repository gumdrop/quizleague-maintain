package quizleague.web.model

import scala.scalajs.js
import scala.scalajs.js.Date
import scala.scalajs.js.Any.fromBoolean
import scala.scalajs.js.Any.fromString
import scala.scalajs.js.annotation.JSExportAll
import angulate2.std.Data

object CompetitionType extends Enumeration {
  type CompetitionType = Value
  val league, cup, subsidiary, singleton = Value
}

import CompetitionType._
import scala.scalajs.js.annotation.ScalaJSDefined
import quizleague.web.util.rx.RefObservable

@ScalaJSDefined
sealed trait Competition extends js.Object {
  val id: String
  val name: String
  val typeName: String
  val fixtures: js.Array[RefObservable[Fixtures]]
  val results: js.Array[RefObservable[Results]]
  val tables: js.Array[RefObservable[LeagueTable]]
  val text: RefObservable[Text]
}

@ScalaJSDefined
class LeagueCompetition(
    override val id: String,
    override val name: String,
    val startTime: String,
    val duration: Float,
    override val fixtures: js.Array[RefObservable[Fixtures]],
    override val results: js.Array[RefObservable[Results]],
    override val tables: js.Array[RefObservable[LeagueTable]],
    override val text: RefObservable[Text],
    val subsidiary: RefObservable[Competition]) extends Competition {
  override val typeName = league.toString()
}

@ScalaJSDefined
class CupCompetition(
    override val id: String,
    override val name: String,
    val startTime: String,
    val duration: Float,
    override val fixtures: js.Array[RefObservable[Fixtures]],
    override val results: js.Array[RefObservable[Results]],
    override val text: RefObservable[Text]) extends Competition {
  override val typeName = cup.toString()
  override val tables = js.Array[RefObservable[LeagueTable]]()
}

@ScalaJSDefined
class SubsidiaryLeagueCompetition(
    override val id: String,
    override val name: String,
    override val results: js.Array[RefObservable[Results]],
    override val tables: js.Array[RefObservable[LeagueTable]],
    override val text: RefObservable[Text]) extends Competition {
  override val typeName = subsidiary.toString()
  override val fixtures = js.Array[RefObservable[Fixtures]]()
}

@ScalaJSDefined
class SingletonCompetition(
    override val id: String,
    override val name: String,
    override val text: Text,
    val textName: String,
    val event: Event) extends Competition {
  override val typeName = singleton.toString()
  override val fixtures = js.Array[RefObservable[Fixtures]]()
  override val results = js.Array[RefObservable[Results]]()
  override val tables = js.Array[RefObservable[LeagueTable]]()
}
    

