package quizleague.web.maintain.team

import angulate2.std._

import angular.material.MaterialModule
import angulate2.forms.FormsModule
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
import quizleague.web.maintain.text.TextService
import quizleague.web.maintain.venue.VenueService
import quizleague.web.maintain.user.UserService
import quizleague.web.maintain._

@NgModule(
  imports = @@[CommonModule,FormsModule,MaterialModule,RouterModule,FlexLayoutModule,TeamRoutesModule],
  declarations = @@[TeamComponent,TeamListComponent],
  providers = @@[TeamService]
   
)
class TeamModule

@Routes(
  root = false,
       Route(
        path = "team/:id",
        component = %%[TeamComponent]
      ),
      Route(
        path = "team",
        component = %%[TeamListComponent]
      )
)
class TeamRoutesModule 


@Injectable
@classModeScala
class TeamService(override val http:Http, 
    override val textService:TextService, 
    override val venueService:VenueService,
    override val userService:UserService) extends TeamGetService with TeamPutService with ServiceRoot

