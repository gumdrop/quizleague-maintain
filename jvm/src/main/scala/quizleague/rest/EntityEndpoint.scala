package quizleague.rest

import javax.ws.rs.Path
import javax.ws.rs.core.Context
import javax.ws.rs.core.Request
import javax.ws.rs.core.UriInfo

@Path("/entity")
class EntityEndpoint(
  @Context override val request:Request,
  @Context override val uriInfo:UriInfo
) extends GetEndpoints with PutEndpoints with PostEndpoints with EtagSupport{
  
  override val defaultCacheAge = 0
  override val shortCacheAge = 0
  
  pre
}