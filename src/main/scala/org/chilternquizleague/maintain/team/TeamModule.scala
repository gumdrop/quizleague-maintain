package org.chilternquizleague.maintain.team

import angulate2.std._
import angular.material.MaterialModule
import angulate2.forms.FormsModule
import angulate2.platformBrowser.BrowserModule
import angulate2.router.{Route,RouterModule}

import scala.scalajs.js
import org.chilternquizleague.maintain.model._
import angulate2.http.Http
import org.chilternquizleague.maintain.service.EntityService
import angular.flexlayout.FlexLayoutModule
import org.chilternquizleague.maintain.component.ComponentNames
import org.chilternquizleague.maintain.component.IdStuff
import angulate2.ext.classModeScala


@NgModule(
  imports = @@[BrowserModule,FormsModule,MaterialModule,RouterModule,FlexLayoutModule],
  declarations = @@[TeamComponent,TeamListComponent],
  providers = @@[TeamService]
   
)
class TeamModule

@Injectable
@classModeScala
class TeamService(override val http:Http) extends EntityService[Team] with TeamId

trait TeamNames extends ComponentNames{
  override val typeName = "team"
}

trait TeamId extends IdStuff[Team]{
  override def getId(team:Team):String = team.id
}



