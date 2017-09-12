package quizleague.rest.endpoint

import javax.ws.rs.Path
import javax.ws.rs.core.Context
import javax.ws.rs.core.Request
import javax.ws.rs.core.UriInfo
import quizleague.rest.EtagSupport
import quizleague.rest.GetEndpoints
import quizleague.rest.MaintainPostEndpoints
import quizleague.rest.PutEndpoints
import javax.ws.rs.POST
import quizleague.domain.container.DomainContainer
import scala.reflect.ClassTag
import quizleague.data.Storage
import quizleague.domain.Entity

@Path("/entity")
class EntityEndpoint(
  @Context override val request:Request,
  @Context override val uriInfo:UriInfo
) extends GetEndpoints with PutEndpoints with MaintainPostEndpoints with EtagSupport{
  
  override val defaultCacheAge = 0
  override val shortCacheAge = 0
  
  preChecks()
  
  @POST
  @Path("/dbupload")
  def dbload(json:String) = {
    import io.circe._, io.circe.generic.auto._, io.circe.syntax._, io.circe.parser._
    import quizleague.util.json.codecs.DomainCodecs._
    import quizleague.util.json.codecs.ScalaTimeCodecs._
    
    def saveAll[T <: Entity](list:List[T])(implicit tag:ClassTag[T], encoder:Encoder[T]) = {
      list.foreach(a => Storage.save[T](a)(tag,encoder))
    }
    
    val container = decode[DomainContainer](json).merge.asInstanceOf[DomainContainer]
    
    saveAll(container.applicationcontext)
    saveAll(container.competition)
    saveAll(container.fixture)
    saveAll(container.fixtures)
    saveAll(container.globaltext)
    saveAll(container.leaguetable)
    saveAll(container.result)
    saveAll(container.results)
    saveAll(container.reports)
    saveAll(container.season)
    saveAll(container.team)
    saveAll(container.text)
    saveAll(container.user)
    saveAll(container.venue)
    
    
    
    
  }
}