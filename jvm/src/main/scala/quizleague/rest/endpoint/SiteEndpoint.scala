package quizleague.rest.endpoint

import javax.ws.rs.core._
import javax.ws.rs._
import quizleague.data.Storage._
import quizleague.util.json.codecs.DomainCodecs._
import quizleague.conversions.RefConversions._
import quizleague.domain._
import quizleague.rest._
import quizleague.util.collection._
import org.threeten.bp.LocalDate
import io.circe.syntax._


@Path("/site")
class SiteEndpoint extends SitePostEndpoints{
  
  implicit val context = StorageContext()
  
  @POST
  @Path("/team-for-email/{email}")
  @Produces(Array("application/json"))
  def teamForEmail(@PathParam("email") email: String) = {
    
    val lce = email.toLowerCase()
    
    list[Team].filter(t => t.users.exists(_.email.toLowerCase == lce)).asJson.noSpaces

  }
  
}