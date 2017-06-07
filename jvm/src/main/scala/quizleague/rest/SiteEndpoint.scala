package quizleague.rest

import javax.ws.rs.Path
import javax.ws.rs.core._


@Path("/site")
class SiteEndpoint(
  @Context override val request:Request,
  @Context override val uriInfo:UriInfo
) extends GetEndpoints with EtagSupport{
  
  override val defaultCacheAge = 3600
  override val shortCacheAge = 300

  pre
}