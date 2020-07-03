package quizleague.rest.endpoint


import javax.ws.rs.core._
import javax.ws.rs._
import quizleague.data._
import quizleague.data.Storage
import Storage._
import quizleague.util.json.codecs.CommandCodecs._
import quizleague.util.json.codecs.DomainCodecs._
import quizleague.conversions.RefConversions._
import quizleague.domain._
import quizleague.domain.stats._
import quizleague.domain.command._
import quizleague.domain.util._
import java.util.UUID.{randomUUID => uuid}
import java.util.logging.Logger

import quizleague.rest._
import io.circe._
import com.google.appengine.api.taskqueue._
import com.google.appengine.api.taskqueue.TaskOptions.Builder._
import javax.ws.rs.PathParam
import quizleague.domain.notification._
import java.time.LocalDateTime

import quizleague.domain



@Path("/task")
class TaskEndpoint {
  
  val logger = Logger.getLogger(this.getClass.getName)
  implicit val context = StorageContext

  @POST
  @Path("/submitresult")
  def resultSubmit(body: String) = {

    val result = deser[ResultsSubmitCommand](body)

    logger.finest(() => s"submit result arrived : $result")

    val haveResults = result.fixtures.exists(f => {
      val fix = Storage.load[Fixture](f.fixtureKey)
      fix.result.isDefined
    })

    val user = load[User](Key(None,"user",result.userID))

    result.fixtures.foreach(saveFixture(user, result.reportText) _)

    if (!haveResults) {

      result.fixtures.foreach(f => {
        val fixture = load[Fixture](f.fixtureKey)
        val isSubsidiary = subsidiary(fixture)
        val leagueTables = tables(fixture)
        if (!leagueTables.isEmpty) {
          updateTables(leagueTables, fixture)

          if (!isSubsidiary) {
            fireStatsUpdate(fixture)
          }
        }
        if (!isSubsidiary) {
          Storage.save(Notification(
            uuid.toString(),
            NotificationTypeNames.result,
            LocalDateTime.now(),
            ResultPayload(fixture.id)))
        }
      })

    }

  }
  
  @POST
  @Path("stats/update/{seasonId}")
  def statsUpdate(body:String, @PathParam("seasonId") seasonId:String){
    
    val fixtures = deser[List[Fixture]](body)
    val season = load[Season](seasonId)
    
    fixtures.foreach(StatsWorker.perform(_, season))
    
  }
  
  @POST
  @Path("stats/regenerate/{seasonId}")
  def statsRegenerate(@PathParam("seasonId") seasonId:String){
    
     val season = load[Season](seasonId)
     
     HistoricalStatsAggregator.perform(season)

     val key = Key(None, "notification", uuid.toString)

     Storage.save(Notification(
         key.id,
         NotificationTypeNames.maintain, 
         LocalDateTime.now(), 
         MaintainMessagePayload(s"Stats regenerated for ${season.startYear}/${season.endYear}")
      ).withKey(key))
    
  }
  
  private def tables(fixture:Fixture):List[LeagueTable] =   { 
    list[LeagueTable](fixture.key.map(_.parentKey).flatMap(_.flatMap(Key(_).parentKey)).map(Key(_)))
  }
  
 private def subsidiary(fixture: Fixture):Boolean = {
   val key = fixture.key.map(_.parentKey).flatMap(_.flatMap(Key(_).parentKey)).map(Key(_)).getOrElse(throw new IllegalArgumentException)
   val competition = Storage.load[Competition](key)
   competition match {
     case _:domain.SubsidiaryCompetition => true
     case _ => false
   }

 }
  
  
  private def fireStatsUpdate(fixture:Fixture){
   import io.circe._, io.circe.syntax._
    
   val queue: Queue = QueueFactory.getQueue("stats");
    
   val season =  applicationContext().currentSeason
    
   queue.add(withUrl(s"/rest/task/stats/update/${season.id}").payload(List(fixture).asJson.toString));
  }
  
  private def updateTables(tables:List[LeagueTable], fixture:Fixture){
    
    logger.finest(() => s"entering updateTables : \nfixture:$fixture") 
    
    logger.finest(() => s"tables : \n$tables") 
    
    val newTables = LeagueTableRecalculator.recalculate(tables, List(fixture))
    
    logger.finest(() => s"new tables : \n$newTables") 
    
    Storage.saveAll(newTables)
    
  }

  private def saveFixture(user:User,reportIn:Option[String])(result:ResultValues) = {
       
    val fixture = Storage.load[Fixture](result.fixtureKey)
    val report = reportIn.filter(r => !r.trim.isEmpty && subsidiary(fixture))
    
    logger.finest(() => s"entering saveFixture : \nuser : $user\nreport : $report\nresult:$result") 
    
    def newText(reportText:String) = {
      val id = uuid.toString
      val text = Text(id, reportText, "text/markdown").withKey(Key(None,"text", id))
      Storage.save(text)
      Ref[Text]("text",text.id)
    }

    def newResult() = {
      Some(Result(result.homeScore,result.awayScore, submitter = Some(Ref[User]("user",user.id)), None))
    }
    
    def newReport(reportText:String) = {
       val team = teamFromUser(user)
       Report(Ref("team",team.id), newText(reportText)).withKey(Key(fixture.key.get,"report",uuid.toString))
    }
    

    logger.finest(() => s"fixture : \n:$fixture") 
    
    val res = fixture.copy(result = fixture.result.fold(newResult())(Some(_))).withKey(fixture.key)

    report.foreach(reportText => Storage.save(newReport(reportText)))
    
    logger.finest(() => s"made result : \nresult:$res") 
    
    Storage.save(res)

  }

  private def userFromEmail(email:String):Option[User] = {

    val users = Storage.list[User]
    
    logger.finest(() => s"users : \n$users") 
    
    val user = users.filter(_.email.toLowerCase == email.toLowerCase).headOption
    
    logger.finest(() => s"user : \n$user")
    
    user
  }
  
  private def teamFromUser(user:User) = {
    
    val teams = Storage.list[Team]
    
    logger.finest(() => s"teams : \n$teams") 
    
    val team = teams.filter(t => t.users.exists(_.id == user.id)).head
    
    logger.finest(() => s"team : \n$team")
    
    team
  }
  
}