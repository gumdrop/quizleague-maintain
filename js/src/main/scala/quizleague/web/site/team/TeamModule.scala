package quizleague.web.site.team

import angular.flexlayout.FlexLayoutModule
import angular.material.MaterialModule
import angulate2.common.CommonModule
import angulate2.ext.classModeScala
import angulate2.http.Http
import angulate2.router.Route
import angulate2.router.RouterModule
import angulate2.std._
import quizleague.web.service.team.TeamGetService
import quizleague.web.site.ServiceRoot
import quizleague.web.site.text.TextModule
import quizleague.web.site.text.TextService
import quizleague.web.site.user.UserService
import quizleague.web.site.venue.VenueService
import quizleague.web.model.Team
import quizleague.web.model.Season
import quizleague.web.site.season.SeasonService
import quizleague.web.site.common.CommonAppModule

@NgModule(
  imports = @@[CommonModule,MaterialModule,RouterModule,FlexLayoutModule,TeamRoutesModule, TextModule, CommonAppModule],
  declarations = @@[TeamComponent, TeamsComponent, TeamMenuComponent, TeamTitleComponent, TeamsTitleComponent],
  providers = @@[TeamService]
   
)
class TeamModule

@Routes(
   root = false,
   Route(
       path = "team",
       children = @@@(
         Route(path = ":id",children = @@@(
             Route(path = "",component = %%[TeamComponent]),
             Route(path = "",component = %%[TeamTitleComponent], outlet="title"))),
         Route(path = "",children = @@@(
             Route(path = "",component = %%[TeamsComponent]),
             Route(path = "",component = %%[TeamsTitleComponent], outlet="title")
             )),
         Route(path = "",component = %%[TeamMenuComponent], outlet="sidemenu")
 
             ))
      

       
)
@classModeScala
class TeamRoutesModule


@Injectable
@classModeScala
class TeamService(override val http:Http, 
    override val textService:TextService, 
    override val venueService:VenueService,
    override val userService:UserService) extends TeamGetService with ServiceRoot{
  

}

