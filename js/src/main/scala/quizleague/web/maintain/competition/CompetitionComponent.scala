package quizleague.web.maintain.competition

import quizleague.web.maintain.component._
import quizleague.web.core._
import quizleague.web.model._
import scalajs.js
import quizleague.web.maintain.season.SeasonService
import rxscalajs.Observable
import scala.scalajs.js.UndefOr
import quizleague.web.maintain.component.ItemComponentConfig._
import quizleague.web.maintain.leaguetable.LeagueTableService

@js.native
trait CompetitionComponent extends ItemComponent[Competition]{
  val season:Season
  
}

trait CompetitionComponentConfig extends ItemComponentConfig[Competition] with RouteComponent{
  
  override type facade <: CompetitionComponent
  
  val service = CompetitionService
  
  def fixtures(c:facade) = {
     c.$router.push(s"fixtures")
   }
  
  def toTable(c:facade, tableId:String) = {
     c.$router.push(s"leaguetable/$tableId")
  }
  
  def removeTable(c:facade, tableId:String) = {
    c.item.tables ---= tableId
  }
  
  def addTable(c:facade) = {
    val table = LeagueTableService.instance()
    c.item.tables +++= (table.id, table)
  }

  subscription("season"){c:facade => SeasonService.get(c.$route.params("seasonId"))}
  
  method("fixtures")({fixtures _}:js.ThisFunction)
  method("toTable")({toTable _}:js.ThisFunction)
  method("removeTable")({removeTable _}:js.ThisFunction)
  method("addTable")({addTable _}:js.ThisFunction)
  
}