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
import quizleague.web.util.rx._
import rxscalajs.Observable
import quizleague.web.service.PostService
import quizleague.domain.command.TeamEmailCommand
import chartjs.chart._
import org.scalajs.dom.ext.Color
import quizleague.web.site.season._
import java.time.LocalDate
import java.time.format.DateTimeFormatter

import quizleague.web.useredit.TeamEditPage
import quizleague.web.site.ApplicationContextService
import quizleague.web.site.competition.CompetitionService
import quizleague.web.model.CompetitionType
import quizleague.util.StringUtils._
import quizleague.web.site.fixtures.FixtureService
import quizleague.web.site.fixtures.FixturesService
import quizleague.web.util.{UUID, rx}
import quizleague.web.util.Logging._

object TeamModule extends Module{
  
  override val components = @@(TeamComponent,TeamTitle, TeamFixturesComponent, TeamNameComponent, TeamResultsComponent,ResponsiveTeamNameComponent)
  
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
  
  def leagueStanding(teamId:String):Observable[js.Array[Standing]] = ApplicationContextService.get.flatMap(
    s => {
      CompetitionService.competition[LeagueCompetition](s.currentSeason.id, CompetitionType.league.toString)
        .flatMap(c => zip(c.tables))
        .map(_.flatMap(t => t.rows
          .filter(_.team.id == teamId)
          .map(row => new Standing(s"League ${t.description}", toOrdinal(row.position.toInt)))))
     
    }
  )
  def cupStandings(teamId:String):Observable[js.Array[Standing]] = ApplicationContextService.get.flatMap(
    s => {
      FixtureService.fixturesFrom(FixturesService.competitionFixtures(CompetitionService.competitionsOfType[CupCompetition](s.currentSeason.id)), teamId)
        .map(_.filter(_.result == null).map(f => new Standing(f.parentDescription,f.description)))
    }
  )

  def standings(teamId:String):Observable[js.Array[Standing]] = combineLatest(leagueStanding(teamId),cupStandings(teamId))
      .map(_.flatMap(x => x))
  
}

object TeamViewService extends SeasonWatchService

object StatisticsService extends StatisticsGetService{
  override val teamService = TeamService
  override val seasonService = SeasonService
  override val tableService = LeagueTableService 
  
  def teamStats(teamId:String, seasonId:String):Observable[Statistics] = {
    
    val q = db.collection(uriRoot).where("team.id","==", teamId).where("season.id","==",seasonId)
    
    query(q).map(_.headOption.fold[Statistics](null)(identity))
  }
  
  def allTeamStats(teamId:String):Observable[js.Array[Statistics]] = {
   
    val q = db.collection(uriRoot).where("team.id","==", teamId)
    
    query(q)
  }
  
  def teamsInTable(stats:Statistics):Observable[Int] = stats.table.map(_.rows.size)
  
  def teamsInTables(stats:js.Array[Statistics]):Observable[Int] = Observable.combineLatest(
      stats.map(_.table.obs).toSeq)
      .map(_.foldLeft(0)((max,t) => if(max > t.rows.size) max else t.rows.size))
  
  private val dateFormat = DateTimeFormatter.ofPattern("d MMM")
  
     private def formatDate(date:String):String = LocalDate.parse(date).format(dateFormat)
  
  private def formatDate(stats:WeekStats):String = formatDate(stats.date)
      
      
 def positionData(stats:Statistics):ChartData = {
    ChartData(
        datasets = js.Array(DataSet("League Position", data = stats.weekStats.map(_.leaguePosition.asInstanceOf[js.Any]),lineTension=.2)), 
        xLabels = stats.weekStats.map(formatDate _),
    )
  }
  
  def matchScoresData(stats:Statistics):ChartData = {
    ChartData(
        datasets = js.Array(
            DataSet("For", data = stats.weekStats.map(s => if(s.ignorable) null else s.pointsFor.asInstanceOf[js.Any]),lineTension=.2, fill=true, backgroundColor="rgba(150,150,150,.5)",borderColor=new Color(50,50,50)),
            DataSet("Against", data = stats.weekStats.map(s => if(s.ignorable) null else s.pointsAgainst.asInstanceOf[js.Any]),lineTension=.2,fill=true, backgroundColor="rgba(150,150,150,.7)", borderColor=Color.Red)    
        ), 
        xLabels = stats.weekStats.map(formatDate _),
    )
  }
  
  def cumuDiffData(stats:Statistics):ChartData = {
    ChartData(
        datasets = js.Array(DataSet("", data = stats.weekStats.map(s => if(s.ignorable) null else s.cumuPointsDifference.asInstanceOf[js.Any]),lineTension=.2)), 
        xLabels = stats.weekStats.map(formatDate _),
    )
  }
  
  def cumuScoresData(stats:Statistics):ChartData = {
    
    ChartData(
        datasets = js.Array(
            DataSet("For", data = stats.weekStats.map(s => if(s.ignorable) null else s.cumuPointsFor.asInstanceOf[js.Any]),lineTension=.2, fill=true, backgroundColor="rgba(150,150,150,.5)",borderColor=new Color(50,50,50)),
            DataSet("Against", data = stats.weekStats.map(s => if(s.ignorable) null else s.cumuPointsAgainst.asInstanceOf[js.Any]),lineTension=.2,fill=true, backgroundColor="rgba(150,150,150,.7)", borderColor=Color.Red)    
        ), 
        xLabels = stats.weekStats.map(formatDate _),
    )
  }
  
  private def sortStats(stats:js.Array[Statistics], seasons:Seq[Season]) = {
     val seasonYears = seasons.map(s => ((s.id,s.startYear))).toMap
     stats.sortBy(s => seasonYears(s.season.id))
  }

  private def sortAndPadStats(stats:js.Array[Statistics], seasons:Seq[Season]) = {
    val seasonYears = seasons.map(s => ((s.id,s.startYear))).toMap
    val padded = seasons.map(s => stats.find(_.season.id == s.id).getOrElse(Statistics.stub(s)))

    padded.sortBy(s => seasonYears(s.season.id)).toJSArray
  }
  
  def allSeasonsPositionData(stats:js.Array[Statistics]):Observable[ChartData] = {
    
    Observable.combineLatest(stats.map(_.season.obs).toSeq)
      .map(seasons => {
       val sortedStats = sortStats(stats,seasons)
       val data:js.Array[js.Any] =  sortedStats.map(x => (if(x.seasonStats.currentLeaguePosition == 0) null else x.seasonStats.currentLeaguePosition).asInstanceOf[js.Any])
        ChartData(
        datasets = js.Array(DataSet("League Position", data = data,lineTension=.2)),
        xLabels = seasons.map(SeasonFormat.format _).toJSArray.sortBy(identity)
        )
      })
  }


  def multipleTeamsAllSeasonsPositionData(stats:js.Array[js.Array[Statistics]]):Observable[ChartData] = {
    Observable.combineLatest(stats(0).map(_.season.obs).toSeq)
      .flatMap(seasons => {

        val datasets = stats.map(s => {
          val sortedStats = sortAndPadStats(s,seasons)
          val team = sortedStats.find(_.team != null).get.team.obs
          val data:js.Array[js.Any] =  sortedStats.map(x => (if(x.seasonStats.currentLeaguePosition == 0) null else x.seasonStats.currentLeaguePosition).asInstanceOf[js.Any])
          team.map(t => {
            val colour = randomColor

            DataSet(t.shortName, data = data, lineTension = .2, borderColor = colour, backgroundColor = colour)
          })}).toSeq

        Observable.combineLatest(datasets).map(d => ChartData(
          datasets = d.toJSArray,
          xLabels = seasons.map(SeasonFormat.format _).toJSArray.sortBy(identity)
        ))


      })
  }

//  def allSeasonsResultsData(stats:js.Array[Statistics], teams:js.Array[RefObservable[Team]]):Observable[ChartData] = {
//    Observable.combineLatest(stats.map(_.season.obs).toSeq)
//      .flatMap(seasons => {
//
//        val datasets = stats.map(s => {
//          val sortedStats = sortAndPadStats(s,seasons)
//          val team = sortedStats.find(_.team != null).get.team.obs
//          val data:js.Array[js.Any] =  sortedStats.map(x => ).asInstanceOf[js.Any])
//          team.map(t => {
//            val colour = randomColor
//
//            DataSet(t.shortName, data = data, lineTension = .2, borderColor = colour, backgroundColor = colour)
//          })}).toSeq
//
//        Observable.combineLatest(datasets).map(d => ChartData(
//          datasets = d.toJSArray,
//          xLabels = seasons.map(SeasonFormat.format _).toJSArray.sortBy(identity)
//        ))
//
//
//      })
//  }

  private def randomColor = new Color((Math.random*255).toInt, (Math.random*255).toInt, (Math.random*255).toInt)

  def allSeasonsAverageData(stats:js.Array[Statistics]):Observable[ChartData] = {
    
    Observable.combineLatest(stats.map(_.season.obs).toSeq)
      .map(seasons => {
       val sortedStats = sortStats(stats,seasons)
       def fixCount(weekStats:js.Array[WeekStats]) = weekStats.count(!_.ignorable)
       ChartData(
        datasets = js.Array(
            DataSet("Average For", data = sortedStats.map(s => (s.seasonStats.runningPointsFor/fixCount(s.weekStats)).asInstanceOf[js.Any]),lineTension=.2,fill=true,borderColor=new Color(50,50,50),backgroundColor="rgba(150,150,150,.5)"),
            DataSet("Average Against", data = sortedStats.map(s => (s.seasonStats.runningPointsAgainst/fixCount(s.weekStats)).asInstanceOf[js.Any]),lineTension=.2,fill=true,borderColor=Color.Red,backgroundColor="rgba(150,150,150,.7)")
    
        ), 
        xLabels = seasons.map(SeasonFormat.format _).toJSArray.sortBy(identity)
        )
      }
      )
  }
  
}
