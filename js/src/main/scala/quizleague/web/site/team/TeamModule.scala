package quizleague.web.site.team

import quizleague.web.core._
import quizleague.web.core.RouteConfig
import scalajs.js
import js.JSConverters._
import quizleague.web.service.team.TeamGetService
import quizleague.web.service.statistics.StatisticsGetService
import quizleague.web.site.text.TextService
import quizleague.web.site.venue.VenueService
import quizleague.web.site.user.UserService
import quizleague.web.site.season.SeasonWatchService
import quizleague.web.site.season.SeasonService
import quizleague.web.site.leaguetable.LeagueTableService
import quizleague.web.service._
import quizleague.web.model._
import rxscalajs.Observable
import quizleague.web.service.PostService
import quizleague.domain.command.TeamEmailCommand
import chartjs.chart._
import language.postfixOps
import org.scalajs.dom.ext.Color
import quizleague.web.site.season._

object TeamModule extends Module{
  
  override val components = @@(TeamComponent,TeamTitle, TeamFixturesComponent, TeamNameComponent, TeamResultsComponent)
  
  override val routes = @@(      
      
      RouteConfig(path = "/team/start", 
          components = Map("default" -> StartTeamPage, "title" -> StartTeamTitleComponent,"sidenav" -> TeamMenuComponent)),
      RouteConfig(path = "/team/edit/:id", 
          components = Map("default" -> TeamEditPage, "title" -> TeamTitleComponent)),
          RouteConfig(path = "/team/:id", 
          components = Map("default" -> TeamPage, "title" -> TeamTitleComponent,"sidenav" -> TeamMenuComponent)),
      RouteConfig(path = "/team/:id/fixtures", 
          components = Map("default" -> TeamFixturesPage, "title" -> TeamFixturesTitle,"sidenav" -> TeamMenuComponent)),
      RouteConfig(path = "/team/:id/results", 
          components = Map("default" -> TeamResultsPage, "title" -> TeamResultsTitle,"sidenav" -> TeamMenuComponent)),
      RouteConfig(path = "/team/:id/stats", 
          components = Map("default" -> TeamStatsPage, "title" -> TeamStatsTitle,"sidenav" -> TeamMenuComponent)),
      RouteConfig(path = "/team", 
          components = Map("default" -> TeamsComponent, "title" -> TeamsTitleComponent,"sidenav" -> TeamMenuComponent)),
  )

      
}

object TeamService extends TeamGetService with RetiredFilter[Team] with PostService{
  
  override val textService = TextService
  override val userService = UserService
  override val venueService = VenueService
  
  def teamForEmail(email:String):Observable[js.Array[Team]] = {
    import quizleague.util.json.codecs.DomainCodecs._
    command[List[U],String](List("site","team-for-email",email),None).map(_.map(mapOutSparse _).toJSArray)
  }
  
  def sendEmailToTeam(sender:String, text:String, team:Team){
    import quizleague.util.json.codecs.CommandCodecs._
    
    val cmd = TeamEmailCommand(sender,text,team.id)
    command[String,TeamEmailCommand](List("site","email","team"),Some(cmd)).subscribe(x => Unit)
  }
  
}

object TeamViewService extends SeasonWatchService

object StatisticsService extends StatisticsGetService{
  override val teamService = TeamService
  override val seasonService = SeasonService
  override val tableService = LeagueTableService 
  
  def teamStats(teamId:String, seasonId:String):Observable[Statistics] = {
    
    println(s"teamId : $teamId")
    println(s"seasonId : $seasonId")
    
    val q = db.collection(uriRoot).where("team.id","==", teamId).where("season.id","==",seasonId)
    
    query(q).map(_.head)
  }
  
  def allTeamStats(teamId:String):Observable[js.Array[Statistics]] = {
   
    val q = db.collection(uriRoot).where("team.id","==", teamId)
    
    query(q)
  }
  
  def teamsInTable(stats:Statistics):Observable[Int] = stats.table.map(_.rows.size)
  
  def positionData(stats:Statistics):ChartData = {
    ChartData(
        datasets = js.Array(DataSet("League Position", data = stats.weekStats.map(_.leaguePosition.asInstanceOf[js.Any]),lineTension=.2)), 
        xLabels = stats.weekStats.map(_.date),
    )
  }
  
  def matchScoresData(stats:Statistics):ChartData = {
    ChartData(
        datasets = js.Array(
            DataSet("For", data = stats.weekStats.map(_.pointsFor.asInstanceOf[js.Any]),lineTension=.2, fill=true, backgroundColor="rgba(150,150,150,.5)",borderColor=new Color(50,50,50)),
            DataSet("Against", data = stats.weekStats.map(_.pointsAgainst.asInstanceOf[js.Any]),lineTension=.2,fill=true, backgroundColor="rgba(150,150,150,.7)", borderColor=Color.Red)    
        ), 
        xLabels = stats.weekStats.map(_.date),
    )
  }
  
  def cumuDiffData(stats:Statistics):ChartData = {
    ChartData(
        datasets = js.Array(DataSet("", data = stats.weekStats.map(_.cumuPointsDifference.asInstanceOf[js.Any]),lineTension=.2)), 
        xLabels = stats.weekStats.map(_.date),
    )
  }
  
  def cumuScoresData(stats:Statistics):ChartData = {
    ChartData(
        datasets = js.Array(
            DataSet("For", data = stats.weekStats.map(_.cumuPointsFor.asInstanceOf[js.Any]),lineTension=.2, fill=true, backgroundColor="rgba(150,150,150,.5)",borderColor=new Color(50,50,50)),
            DataSet("Against", data = stats.weekStats.map(_.cumuPointsAgainst.asInstanceOf[js.Any]),lineTension=.2,fill=true, backgroundColor="rgba(150,150,150,.7)", borderColor=Color.Red)    
        ), 
        xLabels = stats.weekStats.map(_.date),
    )
  }
  
  def allSeasonsPositionData(stats:js.Array[Statistics]):Observable[ChartData] = {
    
    Observable.combineLatest(stats.map(_.season.obs).toSeq)
      .map(seasons => 
       ChartData(
        datasets = js.Array(DataSet("League Position", data = stats.map(_.seasonStats.currentLeaguePosition.asInstanceOf[js.Any]),lineTension=.2)), 
        xLabels = seasons.map(SeasonFormat.format _).toJSArray
        )
      )
  }
  
    def allSeasonsAverageData(stats:js.Array[Statistics]):Observable[ChartData] = {
    
    Observable.combineLatest(stats.map(_.season.obs).toSeq)
      .map(seasons => 
       ChartData(
        datasets = js.Array(
            DataSet("Average For", data = stats.map(s => (s.seasonStats.runningPointsFor/s.weekStats.size).asInstanceOf[js.Any]),lineTension=.2),
            DataSet("Average Against", data = stats.map(s => (s.seasonStats.runningPointsAgainst/s.weekStats.size).asInstanceOf[js.Any]),lineTension=.2)
    
        ), 
        xLabels = seasons.map(SeasonFormat.format _).toJSArray.sortBy(identity)
        )
      )
  }
  
}