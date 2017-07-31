package quizleague.rest.endpoint

import javax.ws.rs.core._
import quizleague.rest.EtagSupport
import quizleague.rest.GetEndpoints
import javax.ws.rs._
import quizleague.data.Storage
import quizleague.domain.ApplicationContext
import quizleague.util.json.codecs.DomainCodecs._
import quizleague.conversions.RefConversions._
import quizleague.domain._
import quizleague.util.collection._


@Path("/site")
class SiteEndpoint(
  @Context override val request:Request,
  @Context override val uriInfo:UriInfo
) extends GetEndpoints with EtagSupport{
  
  override val defaultCacheAge = 3600
  override val shortCacheAge = 300

  preChecks()
  
  @GET
  @Path("/results/latest/{id}")
  def latestResults(@PathParam("id") id:String) = {
    val results = refListToObjectList(Storage.load[Season](id).competitions).flatMap(c => c match{
      case a:TeamCompetition => a.results
      case _ => List()
     })
     .sortBy(_.fixtures.date.toString)(Desc)
     .take(1)
     
    listOut[Results](results)
  }
}