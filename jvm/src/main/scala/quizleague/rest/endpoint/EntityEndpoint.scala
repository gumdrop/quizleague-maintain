package quizleague.rest.endpoint

import javax.ws.rs.Path
import quizleague.rest.MaintainPostEndpoints
import javax.ws.rs.POST
import quizleague.domain.container.DomainContainer
import scala.reflect.ClassTag
import quizleague.data.Storage
import quizleague.domain.Entity
import javax.ws.rs.PathParam
import quizleague.domain._
import quizleague.domain.util._
import quizleague.util.json.codecs.DomainCodecs._
import quizleague.conversions.RefConversions._
import quizleague.rest._ 
import io.circe._

@Path("/entity")
class EntityEndpoint extends MaintainPostEndpoints{
  
  implicit val context = StorageContext()
  
  @POST
  @Path("/dbupload")
  def dbload(json:String) = {
    
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
}