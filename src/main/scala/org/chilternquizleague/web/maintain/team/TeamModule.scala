package org.chilternquizleague.web.maintain.team

import angulate2.std._

import angular.material.MaterialModule
import angulate2.forms.FormsModule
import angulate2.router.{Route,RouterModule}

import scala.scalajs.js
import org.chilternquizleague.web.model._
import org.chilternquizleague.domain.{Team => DomTeam}
import angulate2.http.Http
import org.chilternquizleague.web.service.EntityService
import angular.flexlayout.FlexLayoutModule
import org.chilternquizleague.web.maintain.component.ComponentNames

import angulate2.ext.classModeScala
import angulate2.common.CommonModule
import org.chilternquizleague.web.service.team._
import org.chilternquizleague.web.maintain.text.TextService
import org.chilternquizleague.web.maintain.venue.VenueService
import org.chilternquizleague.web.maintain.user.UserService
import org.chilternquizleague.web.maintain._

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

trait TeamNames extends ComponentNames{
  override val typeName = "team"
}

@Injectable
@classModeScala
class TeamService(override val http:Http, 
    override val textService:TextService, 
    override val venueService:VenueService,
    override val userService:UserService) extends TeamGetService with TeamPutService with ServiceRoot

