package quizleague.web.site.team

import angulate2.std._

import angular.material.MaterialModule
import angulate2.router.{Route,RouterModule}

import scala.scalajs.js
import quizleague.web.model._
import quizleague.domain.{Team => DomTeam}
import angulate2.http.Http
import quizleague.web.service.EntityService
import angular.flexlayout.FlexLayoutModule
import quizleague.web.names.ComponentNames

import angulate2.ext.classModeScala
import angulate2.common.CommonModule
import quizleague.web.service.team._
import quizleague.web.site.text.TextService
import quizleague.web.site.venue.VenueService
import quizleague.web.site.user.UserService
import quizleague.web.site._

@NgModule(
  imports = @@[CommonModule,MaterialModule,RouterModule,FlexLayoutModule,TeamRoutesModule],
  declarations = @@[TeamComponent, TeamsComponent, TeamMenuComponent, TeamTitleComponent],
  providers = @@[TeamService]
   
)
class TeamModule

@Routes(
   root = false,
   Route(
       path = "team",
       children = @@@(
         Route(path = ":id",component = %%[TeamComponent]),
         Route(path = ":id",component = %%[TeamTitleComponent], outlet="title"), 
         Route(path = "",component = %%[TeamsComponent]),
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
    override val userService:UserService) extends TeamGetService with ServiceRoot

