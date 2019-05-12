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
      val siteUser = SiteUser(UUID.randomUUID().toString,"https://storage.googleapis.com/chiltern-ql-firestore.appspot.com/files/defaultAvatar.png","", user.map(u => new Ref[User]("user",u.id)), None)
      save(siteUser)
      siteUser
    }


    val lce = email.toLowerCase()

    val user = list[User].find(_.email.toLowerCase == lce)
    def team(userID:String) = list[Team].find(t => t.users.exists(_.id ==userID)).isDefined

    val siteUser = user.filter(u => team(u.id)).map(u => list[SiteUser].find(su => su.user.map(_.id == u.id).getOrElse(false)))

    val result:Option[SiteUser] = siteUser.flatMap(l => if(l.isDefined) l else l.map(u => createAndSave(user)))

    result.asJson.noSpaces



  }

  @POST
  @Path("/save-site-user")
  @Produces(Array("application/json"))
  def saveSiteUser(body:String) ={

    val in:SiteUser = deser[SiteUser](body)
    val existing = load[SiteUser](in.id).copy(handle = in.handle, avatar = in.avatar)

    save(existing)
    existing
  }


}