package quizleague.rest.endpoint

import javax.ws.rs.Path
import quizleague.rest.MaintainPostEndpoints
import javax.ws.rs.POST
import quizleague.domain.container.DomainContainer
import scala.reflect.ClassTag
import quizleague.data.Storage
import Storage._
import quizleague.domain.Entity
import javax.ws.rs.PathParam
import quizleague.domain._
import quizleague.domain.util._
import quizleague.util.json.codecs.DomainCodecs._
import quizleague.conversions.RefConversions._
import quizleague.rest._ 
import io.circe._
import javax.ws.rs.GET
import javax.ws.rs.Produces
import com.google.appengine.api.taskqueue._
import com.google.appengine.api.taskqueue.TaskOptions.Builder._
import quizleague.rest.endpoint.HistoricalStatsAggregator

@Path("/entity")
class EntityEndpoint extends MaintainPostEndpoints{
  
  implicit val context = StorageContext()
  
  @POST
  @Path("/dbupload")
  def dbupload(json:String) = {
    
    def saveAll[T <: Entity](list:List[T])(implicit tag:ClassTag[T], encoder:Encoder[T]) = {
      Storage.saveAll[T](list)(tag,encoder)
    }
    
    val container = deser[DomainContainer](json)
    
    saveAll(container.applicationcontext)
    saveAll(container.competition)
    saveAll(container.fixture)
    saveAll(container.fixtures)
    saveAll(container.globaltext)
    saveAll(container.leaguetable)
    saveAll(container.reports)
    saveAll(container.season)
    saveAll(container.team)
    saveAll(container.text)
    saveAll(container.user)
    saveAll(container.venue)
    
    
    
    
  }
  
  @GET
  @Path("/dbdownload/dump.json")
  @Produces(Array("application/json"))
  def dbdownload() = {
    import Storage.list
    import io.circe.syntax._

    
    val container = DomainContainer(
        list[ApplicationContext],
        list[Competition],
        list[Fixtures],
        list[Fixture],
        list[GlobalText],
        list[LeagueTable],
        list[Reports],
        list[Season],
        list[Team],
        list[Text],
        list[User],
        list[Venue]
    )
    
    container.asJson.noSpaces
  }
  
  @POST
  @Path("/recalculate-table/{tableId}/{competitionId}")
  def recalculateTable(
      @PathParam("tableId") tableId: String,
      @PathParam("competitionId") competitionId: String
      ) = {

        val table = Storage.load[LeagueTable](tableId)   
        val competition = Storage.load[Competition](competitionId).asInstanceOf[LeagueCompetition]
        
        val blankTable = table.copy(rows = table.rows.map(_.copy(won=0,lost=0,drawn=0,leaguePoints=0,matchPointsFor=0, matchPointsAgainst=0, played=0)))
        
        val recalcTable = LeagueTableRecalculator.recalculate(List(blankTable), competition.fixtures.flatMap(_.fixtures))
        
        recalcTable.foreach(Storage.save(_))
  }
  
  @POST
  @Path("/regenerate-stats/{seasonId}")
  def regenerateStats(@PathParam("seasonId") seasonId: String) {

   val queue: Queue = QueueFactory.getQueue("stats");
    
   queue.add(withUrl(s"/rest/task/stats/regenerate/$seasonId"));
    
//    val season = load[Season](seasonId)
//    
//    HistoricalStatsAggregator.perform(season)
   

  }
}