package quizleague.rest.endpoint

import javax.ws.rs.Path
import javax.ws.rs.core._
import quizleague.rest.EtagSupport
import quizleague.rest.GetEndpoints
import quizleague.rest.PutEndpoints

@Path("/secure")
class SecureEndpoint(
  @Context override val request:Request,
  @Context override val uriInfo:UriInfo
) extends GetEndpoints with PutEndpoints with EtagSupport{
  
  override val defaultCacheAge = 0
  override val shortCacheAge = 0

  preChecks()
}