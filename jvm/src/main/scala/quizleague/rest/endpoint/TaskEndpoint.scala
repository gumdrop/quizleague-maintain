package quizleague.rest.endpoint


import javax.ws.rs.core._
import javax.ws.rs._
import quizleague.data.Storage
import quizleague.util.json.codecs.CommandCodecs._
import quizleague.util.json.codecs.DomainCodecs._
import quizleague.conversions.RefConversions._
import quizleague.domain._
import quizleague.domain.command._
import quizleague.util.collection._
import java.util.UUID.{randomUUID => uuid}
import java.util.logging.Logger



@Path("/task")
class TaskEndpoint {
  
  val logger = Logger.getLogger(this.getClass.getName)
  implicit val context = StorageContext
  
  @POST
  @Path("/submitresult")
  def resultSubmit(body:String) = {
    import io.circe._, io.circe.generic.auto._, io.circe.parser._
   
   val result = decode[ResultsSubmitCommand](body).merge.asInstanceOf[ResultsSubmitCommand]
    
   logger.finest(s"submit result arrived : $result") 
   
   userFromEmail(result.email).foreach(u => saveFixture(u,result.reportText) _)
   
  }

  private def saveFixture(user:User,report:Option[String])(result:ResultValues) = {
       
    logger.finest(s"entering saveFixture : \nuser : $user\nreport : $report\nresult:$result") 
    
    def newText(reportText:String) = {
      val text = new Text(uuid.toString(), reportText, "text/plain")
      Storage.save(text)
      Ref[Text](text.id,"text")
    }
    
    def newReports(reportText:String) = {

      val reports = Reports(uuid.toString(), List(newReport(reportText)))
      Storage.save(reports)
      Ref[Reports](reports.id,"reports")
    }
    
    def newResult() = {
      Some(Result(result.homeScore,result.awayScore, submitter = Some(Ref[User](user.id,"user")), None, report.map(newReports(_))))
    }
    
    def newReport(reportText:String) = {
       val team = teamFromUser(user)
       Report(Ref(team.id,"team"), newText(reportText))
    }
    
    def addReport(ref:Reports, reportText:String) = {
      val newRef = ref.copy(reports = ref.reports.:+(newReport(reportText)))
      Storage.save(newRef)
      Ref[Reports](ref.id,"reports")
    }
    
    def oldResult(existing:Result) = {
      val reports = report.fold(existing.reports)(t => Some(existing.reports.fold(newReports(t))(addReport(_, t))))
      Some(existing.copy(reports = reports))

    }
    
    
    val fixture = Storage.load[Fixture](result.fixtureId)
    
    val res = fixture.copy(result = fixture.result.fold(newResult())(oldResult _))
    
    logger.finest(s"made result : \nresult:$res") 
    
    Storage.save(res)

  }

  private def userFromEmail(email:String):Option[User] = {
    import io.circe._, io.circe.parser._

    Storage.list[User].filter(_.email == email).headOption
  }
  
  private def teamFromUser(user:User) = {
    Storage.list[Team].filter(t => t.users.exists(_.id == user.id)).head
  }
  
}