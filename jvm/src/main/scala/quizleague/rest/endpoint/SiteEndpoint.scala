package quizleague.rest.endpoint

import javax.ws.rs.Path
import javax.ws.rs.core._
import quizleague.rest.EtagSupport
import quizleague.rest.GetEndpoints


@Path("/site")
class SiteEndpoint(
  @Context override val request:Request,
  @Context override val uriInfo:UriInfo
) extends GetEndpoints with EtagSupport{
  
  override val defaultCacheAge = 3600
  override val shortCacheAge = 300

  preChecks()
}