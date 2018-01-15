package quizleague.rest.result

import javax.ws.rs.core._
import quizleague.rest.EtagSupport
import quizleague.rest.GetEndpoints
import javax.ws.rs._
import quizleague.data.Storage
import quizleague.util.json.codecs.CommandCodecs._
import quizleague.conversions.RefConversions._
import quizleague.domain._
import quizleague.util.collection._
import org.threeten.bp.LocalDate

trait ResultHandler {
  
  @POST
  @Path("/result/submit")
  def resultSubmit(body:String) = {
    
  }
  
}