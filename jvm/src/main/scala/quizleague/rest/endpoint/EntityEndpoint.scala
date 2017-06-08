package quizleague.rest.endpoint

import javax.ws.rs.Path
import javax.ws.rs.core.Context
import javax.ws.rs.core.Request
import javax.ws.rs.core.UriInfo
import quizleague.rest.EtagSupport
import quizleague.rest.GetEndpoints
import quizleague.rest.MaintainPostEndpoints
import quizleague.rest.PutEndpoints

@Path("/entity")
class EntityEndpoint(
  @Context override val request:Request,
  @Context override val uriInfo:UriInfo
) extends GetEndpoints with PutEndpoints with MaintainPostEndpoints with EtagSupport{
  
  override val defaultCacheAge = 0
  override val shortCacheAge = 0
  
  preChecks()
}