package org.chilternquizleague.maintain.team

import angulate2.std._
import angular.material.MaterialModule
import angulate2.forms.FormsModule
import angulate2.router.{Route,RouterModule}

import scala.scalajs.js
import org.chilternquizleague.maintain.model._
import org.chilternquizleague.maintain.domain.{Team => DomTeam}
import angulate2.http.Http
import org.chilternquizleague.maintain.service.EntityService
import angular.flexlayout.FlexLayoutModule
import org.chilternquizleague.maintain.component.ComponentNames
import org.chilternquizleague.maintain.component.IdStuff
import angulate2.ext.classModeScala
import angulate2.common.CommonModule


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

