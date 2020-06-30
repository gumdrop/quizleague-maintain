package quizleague.web.model

import scala.scalajs.js
import scala.scalajs.js.Date
import scala.scalajs.js.Any.fromBoolean
import scala.scalajs.js.Any.fromString
import scala.scalajs.js.annotation.JSExportAll
import rxscalajs.Observable

object CompetitionType extends Enumeration {
  type CompetitionType = Value
  val league, cup, subsidiary, singleton = Value
}

import CompetitionType._
import scala.scalajs.js.annotation.ScalaJSDefined
import quizleague.web.util.rx.RefObservable

sealed trait Competition extends Model{
  val id: String
  val name: String
  val typeName: String
  val fixtures: Observable[js.Array[Fixtures]]
  val leaguetable: Observable[js.Array[LeagueTable]]
  val text: RefObservable[Text]
  val textName:String
  val icon:String
}

class LeagueCompetition(
  override val id: String,
  override val name: String,
  val startTime: String,
  val duration: Float,
  override val fixtures: Observable[js.Array[Fixtures]],
  override val leaguetable: Observable[js.Array[LeagueTable]],
  override val text: RefObservable[Text],
  override val textName:String,
  val icon:String) extends Competition {
  override val typeName = league.toString()
}

class CupCompetition(
  override val id: String,
  override val name: String,
  val startTime: String,
  val duration: Float,
  override val fixtures: Observable[js.Array[Fixtures]],
  override val text: RefObservable[Text],  
  override val textName:String,
  val icon:String
  )  extends Competition {
  override val typeName = cup.toString()
  override val leaguetable: Observable[js.Array[LeagueTable]] = Observable.just(js.Array())
}

class SubsidiaryLeagueCompetition(
  override val id: String,
  override val name: String,
  override val fixtures: Observable[js.Array[Fixtures]],
  override val leaguetable: Observable[js.Array[LeagueTable]],
  override val text: RefObservable[Text],
  override val textName:String,
  val icon:String) extends Competition {
  override val typeName = subsidiary.toString()
}
object SubsidiaryLeagueCompetition {
  def addFixtures(sub:Competition,fixtures: Observable[js.Array[Fixtures]]) = {
    new SubsidiaryLeagueCompetition(sub.id,sub.name, fixtures, sub.leaguetable, sub.text, sub.textName, sub.icon)
  }
}

class SingletonCompetition(
  override val id: String,
  override val name: String,
  override val text: RefObservable[Text],
  val textName: String,
  val event: Event,
  val icon:String) extends Competition {
  override val typeName = singleton.toString()
  override val fixtures = Observable.just(js.Array())
  override val leaguetable: Observable[js.Array[LeagueTable]] = Observable.just(js.Array())
}
    

