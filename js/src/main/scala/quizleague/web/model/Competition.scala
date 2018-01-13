package quizleague.web.model

import scala.scalajs.js
import scala.scalajs.js.Date
import scala.scalajs.js.Any.fromBoolean
import scala.scalajs.js.Any.fromString
import scala.scalajs.js.annotation.JSExportAll

object CompetitionType extends Enumeration {
  type CompetitionType = Value
  val league, cup, subsidiary, singleton = Value
}

import CompetitionType._
import scala.scalajs.js.annotation.ScalaJSDefined
import quizleague.web.util.rx.RefObservable

sealed trait Competition extends js.Object with Model{
  val id: String
  val name: String
  val typeName: String
  val fixtures: js.Array[RefObservable[Fixtures]]
  val tables: js.Array[RefObservable[LeagueTable]]
  val text: RefObservable[Text]
  val textName:String
  val icon:String
}

class LeagueCompetition(
  override val id: String,
  override val name: String,
  val startTime: String,
  val duration: Float,
  override val fixtures: js.Array[RefObservable[Fixtures]],
  override val tables: js.Array[RefObservable[LeagueTable]],
  override val text: RefObservable[Text],
  override val textName:String,
  val subsidiary: RefObservable[Competition],
  val icon:String) extends Competition {
  override val typeName = league.toString()
}

class CupCompetition(
  override val id: String,
  override val name: String,
  val startTime: String,
  val duration: Float,
  override val fixtures: js.Array[RefObservable[Fixtures]],
  override val text: RefObservable[Text],  
  override val textName:String,
  val icon:String
  )  extends Competition {
  override val typeName = cup.toString()
  override val tables = js.Array[RefObservable[LeagueTable]]()
}

class SubsidiaryLeagueCompetition(
  override val id: String,
  override val name: String,
  override val tables: js.Array[RefObservable[LeagueTable]],
  override val text: RefObservable[Text],
  override val textName:String,
  val icon:String) extends Competition {
  override val typeName = subsidiary.toString()
  override val fixtures = js.Array[RefObservable[Fixtures]]()
}

class SingletonCompetition(
  override val id: String,
  override val name: String,
  override val text: RefObservable[Text],
  val textName: String,
  val event: Event,
  val icon:String) extends Competition {
  override val typeName = singleton.toString()
  override val fixtures = js.Array[RefObservable[Fixtures]]()
  override val tables = js.Array[RefObservable[LeagueTable]]()
}
    

