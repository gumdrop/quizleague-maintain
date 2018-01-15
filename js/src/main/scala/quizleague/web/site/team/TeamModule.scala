package quizleague.web.site.team

import quizleague.web.core._
import quizleague.web.core.RouteConfig
import scalajs.js
import quizleague.web.service.team.TeamGetService
import quizleague.web.site.text.TextService
import quizleague.web.site.venue.VenueService
import quizleague.web.site.user.UserService
import quizleague.web.site.season.SeasonWatchService
import quizleague.web.service._
import quizleague.web.model._
import rxscalajs.Observable
import quizleague.web.service.PostService
import quizleague.domain.command.TeamEmailCommand

object TeamModule extends Module{
  
  override val components = @@(TeamComponent,TeamTitle, TeamFixturesComponent, TeamNameComponent, TeamResultsComponent)
  
  override val routes = @@(      
      RouteConfig(path = "/team", 
          components = Map("default" -> TeamsComponent, "title" -> TeamsTitleComponent,"sidenav" -> TeamMenuComponent)),
      RouteConfig(path = "/team/:id", 
          components = Map("default" -> TeamPage, "title" -> TeamTitleComponent,"sidenav" -> TeamMenuComponent)),
      RouteConfig(path = "/team/:id/fixtures", 
          components = Map("default" -> TeamFixturesPage, "title" -> TeamTitleComponent,"sidenav" -> TeamMenuComponent)),
      RouteConfig(path = "/team/:id/results", 
          components = Map("default" -> TeamResultsPage, "title" -> TeamResultsTitle,"sidenav" -> TeamMenuComponent))

  )

      
}

object TeamService extends TeamGetService with RetiredFilter[Team] with PostService{
  
  override val textService = TextService
  override val userService = UserService
  override val venueService = VenueService
  
  def teamForEmail(email:String):Observable[js.Array[Team]] = {
    userService.userForEmail(email).combineLatestWith(list())((u,teams) => u.fold(js.Array[Team]())(user => teams.filter(_.users.exists(_.id == user.id))))
    

  }
  
  def sendEmailToTeam(sender:String, text:String, team:Team){
    import quizleague.util.json.codecs.CommandCodecs._
    
    val cmd = TeamEmailCommand(sender,text,team.id)
    command[String,TeamEmailCommand](List("email","team"),Some(cmd)).subscribe(x => Unit)
  }
  
}

object TeamViewService extends SeasonWatchService