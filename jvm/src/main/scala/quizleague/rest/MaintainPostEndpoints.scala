package quizleague.rest

import javax.ws.rs._
import quizleague.data.Storage
import quizleague.domain._
import quizleague.util.json.codecs.DomainCodecs._
import quizleague.conversions.RefConversions._
import quizleague.util.LeagueTableCalculator
import io.circe.parser._,io.circe.syntax._
import quizleague.util.json.codecs.DomainCodecs._


trait MaintainPostEndpoints {
  
//  @POST
//  @Path("/leaguetable/{tableid}/competition/{compid}/recalc")
//  def recalculateTables(@PathParam("compid") compid: String, @PathParam("tableid") tableid: String) = {
//    
//    val competition = Storage.load[Competition](compid).asInstanceOf[TeamCompetition]
//    
//    val results:List[Results] = competition.results
//   
//    val table = Storage.load[LeagueTable](tableid)
//    
//    LeagueTableCalculator.recalculate(table, results).asJson.noSpaces.toString
//
//  }
  
}