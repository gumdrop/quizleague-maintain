package quizleague.rest

import javax.ws.rs._
import quizleague.data.Storage._
import quizleague.domain._
import quizleague.util.json.codecs.DomainCodecs._
import quizleague.util.json.codecs.CommandCodecs._
import com.google.appengine.api.taskqueue.Queue
import com.google.appengine.api.taskqueue.QueueFactory
import com.google.appengine.api.taskqueue.TaskOptions.Builder._
import quizleague.domain.command.TeamEmailCommand
import quizleague.rest.mail.EmailSender
import io.circe._, io.circe.syntax._

trait SitePostEndpoints {
  @POST
  @Path("/result/submit")
  def resultSubmit(body:String) = {

    
    val queue: Queue = QueueFactory.getQueue("results")
    queue.add(withUrl("/rest/task/submitresult").payload(body))
    List[String]().asJson.noSpaces

  }
  
  @POST
  @Path("/contact/team")
  def contactTeam(body:String) = {
    
    import io.circe.parser._
   
    val mail = deser[TeamEmailCommand](body)
   
    EmailSender(mail.sender, load[Team](mail.teamId), mail.text)
    List[String]().asJson.noSpaces

  }
}