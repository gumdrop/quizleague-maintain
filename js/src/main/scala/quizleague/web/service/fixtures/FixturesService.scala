package quizleague.web.service.fixtures


import quizleague.web.service.EntityService
import quizleague.web.model._
import quizleague.web.model.{Fixtures => Model}
import quizleague.domain.{Fixtures => Dom}
import quizleague.domain.Ref
import quizleague.web.names.ComponentNames
import quizleague.util.collection._

import scala.scalajs.js
import java.time.Year

import quizleague.web.util.DateTimeConverters._

import scala.scalajs.js.Date
import quizleague.web.service._
import java.time.LocalTime
import java.time.Duration
import java.time.LocalDate
import java.time.LocalDate

import quizleague.web.service.DirtyListService
import quizleague.web.names.FixturesNames
import quizleague.web.util.rx._
import io.circe._
import io.circe.parser._
import io.circe.syntax._
import quizleague.util.json.codecs.DomainCodecs._
import quizleague.web.service.competition.CompetitionGetService

import js.JSConverters._



trait FixturesGetService extends GetService[Fixtures] with FixturesNames{
    override type U = Dom
    
  val fixtureService:FixtureGetService
  val competitionService:CompetitionGetService

  override protected def mapOutSparse(dom:Dom) = Model(
    dom.id,
    dom.description,
    dom.date,
    dom.start,
    fixtureService.list(dom.key),
    competitionService.get(Key(dom.key.get.parentKey.get)))
  
  override protected def dec(json:js.Any) = decodeJson[U](json)
 
}

trait FixturesPutService extends PutService[Fixtures] with FixturesGetService{
  
  override val fixtureService:FixturePutService
  override protected def mapIn(model:Model) = Dom(model.id, model.description, model.date, model.start)
  override protected def make() = Dom(newId, "",LocalDate.now,LocalTime.of(20,30))
  
  def instance(competition:Competition, fixtures:js.Array[Fixtures]) = {
    
    def findNextDate(c:LeagueCompetition) = {
      (fixtures.sortBy(_.date)(Desc)).headOption.map(x => LocalDate parse(x.date).plusWeeks(1)).getOrElse(dateToLocalDate(new Date(Date.now())))
    }
    
    def weekText = s"Week ${fixtures.length + 1}"
    
    val id = newId
    add(
      (competition match {
      case c:LeagueCompetition => Dom(id, weekText, findNextDate(c), c.startTime)
      case c:CupCompetition => Dom(id,"",LocalDate.now,c.startTime)
      case _ => null
    }).withKey(key(key(competition.key.key,id))))
  }
  
  def copy(in:Fixtures, parentDescription:String, fixtures:js.Array[RefObservable[Fixture]], subsidiary:Boolean):Fixtures = {
    println("copy fixtures")
    
    val fx = mapIn(in)
    val dom = Dom(newId,fx.description, fx.date,fx.start)
    save(dom)
    mapOutSparse(dom)    
  }

  override def enc(item: Dom) = item.asJson
  
//  override def save(item:Dom) = {fixtureService.saveAllDirty;super.save(item)}
//  override def delete(id:String) = {
//    get(id).first.subscribe( fix => {
//      fix.fixtures.foreach(f => {fixtureService.delete(f.id)});
//      super.delete(id)
//    })
//  }

}

