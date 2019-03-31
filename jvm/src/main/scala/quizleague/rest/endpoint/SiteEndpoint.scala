package quizleague.rest.endpoint

import javax.ws.rs.core._
import javax.ws.rs._
import quizleague.data.Storage._
import quizleague.util.json.codecs.DomainCodecs._
import quizleague.conversions.RefConversions._
import quizleague.domain._
import quizleague.rest._
import quizleague.util.collection._
import java.time.LocalDate
import java.util.UUID

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

  @POST
  @Path("/site-user-for-email/{email}")
  @Produces(Array("application/json"))
  def siteUserForEmail(@PathParam("email") email:String) ={

    def createAndSave(user:Option[User]):SiteUser = {
      val siteUser = SiteUser(UUID.randomUUID().toString,"","", user.map(u => new Ref[User]("user",u.id)))
      save(siteUser)
      siteUser
    }


    val lce = email.toLowerCase()

    val user = list[User].filter(_.email.toLowerCase == lce).headOption

    val siteUser = user.map(u => list[SiteUser].filter(su => su.user.map(_.id == u.id).getOrElse(false)))

    val result:Option[List[SiteUser]] = siteUser.map(l => if(l.isEmpty) List(createAndSave(user)) else l)

    result.getOrElse(List()).asJson.noSpaces



  }
  
}