package quizleague.web.maintain.competition

import quizleague.web.maintain.component._
import quizleague.web.core._
import quizleague.web.maintain.competition.LeagueCompetitionComponent.subscription
import quizleague.web.model._

import scalajs.js
import quizleague.web.maintain.season.SeasonService
import rxscalajs.Observable

import scala.scalajs.js.UndefOr
import quizleague.web.maintain.component.ItemComponentConfig._
import quizleague.web.maintain.leaguetable.LeagueTableService
import quizleague.web.util.Logging

@js.native
trait CompetitionComponent extends ItemComponent[Competition]{
  val season:Season
  val tables:js.Array[LeagueTable]
  
}

trait CompetitionComponentConfig extends ItemComponentConfig[Competition] with RouteComponent{
  
  override type facade <: CompetitionComponent
  
  val service = CompetitionService

  def parentKey(c:facade) = new Key(null, "season", c.$route.params("seasonId")).key
  
  def fixtures(c:facade) = {
     c.$router.push(s"fixtures")
   }
  
  def toTable(c:facade, tableId:String) = {
     c.$router.push(s"leaguetable/$tableId")
  }
  
  def removeTable(c:facade, table:LeagueTable) = {
    if(org.scalajs.dom.window.confirm("Delete ?"))
      {
        LeagueTableService.delete(table)
      }
  }
  
  def addTable(c:facade) = {
    val table = LeagueTableService.instance(c.item.key)
    LeagueTableService.save(table)
  }

  subscription("season"){c:facade => SeasonService.get(c.$route.params("seasonId"))}
  
  method("fixtures")({fixtures _}:js.ThisFunction)
  method("toTable")({toTable _}:js.ThisFunction)
  method("removeTable")({removeTable _}:js.ThisFunction)
  subscription("tables")(c => item(c).flatMap(_.leaguetable))
  method("addTable")({addTable _}:js.ThisFunction)
  
}