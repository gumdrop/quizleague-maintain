package quizleague.rest

import javax.ws.rs._
import javax.ws.rs.core._
import javax.ws.rs.core.HttpHeaders._
import io.circe._, io.circe.syntax._
import quizleague.data.Storage
import quizleague.domain._
import quizleague.util.json.codecs.DomainCodecs._
import scala.reflect.ClassTag
import com.google.appengine.api.taskqueue.Queue
import com.google.appengine.api.taskqueue.QueueFactory
import com.google.appengine.api.taskqueue.TaskOptions.Builder._

trait SitePostEndpoints {
  @POST
  @Path("/result/submit")
  def resultSubmit(body:String) = {
    
    val queue: Queue = QueueFactory.getQueue("results");
    queue.add(withUrl("/tasks/results/submit").payload(body));
    
    
    "Done"
    
  }
}