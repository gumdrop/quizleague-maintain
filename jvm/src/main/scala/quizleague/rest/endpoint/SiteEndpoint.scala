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
import org.threeten.bp.LocalDate


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
    implicit val context = StorageContext()
    val results = refListToObjectList(Storage.load[Season](id).competitions).flatMap(c => c match{
      case a:TeamCompetition => a.results
      case _ => List()
     })
     .sortBy(_.fixtures.date.toString)(Desc)
     .take(1)
     
    listOut[Results](results)
  }
  
  @GET
  @Path("/fixtures/next/{id}")
  def nextFixtures(@PathParam("id") id:String) = {
    implicit val context = StorageContext()
    val now = LocalDate.now
    val fixtures = refListToObjectList(Storage.load[Season](id).competitions).flatMap(c => c match{
      case a:TeamCompetition => a.fixtures.filter(_.date.isAfter(now))
      case _ => List()
     })
     .sortBy(_.date.toString)
     .take(1)
     
    listOut[Fixtures](fixtures)
  }
  
  @GET
  @Path("/result/season/{seasonId}/team/{teamId}")
  def teamResults(@PathParam("seasonId") seasonId:String, @PathParam("teamId") teamId:String, @QueryParam("take") take:Int = Integer.MAX_VALUE) = {
    implicit val context = StorageContext()
    val results = refListToObjectList(Storage.load[Season](seasonId).competitions).flatMap(c => c match{
      case a:TeamCompetition => a.results
      case _ => List()
     })
     .flatMap(_.results.filter(r => r.fixture.home.id == teamId || r.fixture.away.id == teamId))
     .sortBy(_.fixture.date.toString)(Desc)
     .take(take)
     
    listOut[Result](results)
  }
  
  @GET
  @Path("/fixture/team/{id}")
  def teamFixtures(@PathParam("seasonId") seasonId:String, @PathParam("teamId") teamId:String, @QueryParam("take") take:Int = Integer.MAX_VALUE) = {
    implicit val context = StorageContext()
    val now = LocalDate.now
    val fixtures = refListToObjectList(Storage.load[Season](seasonId).competitions).flatMap(c => c match{
      case a:TeamCompetition => a.fixtures.filter(_.date.isAfter(now))
      case _ => List()
     })
     .flatMap(_.fixtures.filter(f => f.home.id == teamId || f.away.id == teamId))
     .sortBy(_.date.toString)(Desc)
     .take(take)
     
    listOut[Fixture](fixtures)
  }
}