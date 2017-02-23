package quizleague.web.maintain.fixtures

import angulate2.std._
import angular.material.MaterialModule
import angulate2.forms.FormsModule
import angulate2.router.{Route,RouterModule}

import scala.scalajs.js

import angulate2.http.Http
import quizleague.web.service.EntityService
import angular.flexlayout.FlexLayoutModule
import quizleague.web.maintain.component.ComponentNames

import quizleague.web.maintain._
import angulate2.common.CommonModule
import quizleague.web.maintain.competition.CompetitionComponent
import quizleague.web.maintain.competition.CompetitionModule
import quizleague.web.service.fixtures._
import angulate2.ext.classModeScala

@NgModule(
  imports = @@[CommonModule,FormsModule,MaterialModule,RouterModule,FlexLayoutModule, FixturesRoutesModule],
  declarations = @@[FixturesComponent,FixturesListComponent],
  providers = @@[FixturesService, FixtureService]
   
)
class FixturesModule

@Routes(
  root = false,
      Route(
        path = "_fixtures",
        component = %%[FixturesListComponent]
      )
)
class FixturesRoutesModule 

trait FixturesNames extends ComponentNames{
  override val typeName = "fixtures"
}

trait FixtureNames extends ComponentNames{
  override val typeName = "fixture"
}

@Injectable
@classModeScala
class FixturesService(
    override val http:Http, 
    override val fixtureService:FixtureService) extends FixturesGetService with FixturesPutService with ServiceRoot

@Injectable
@classModeScala
class FixtureService(override val http:Http) extends FixtureGetService with FixturePutService with ServiceRoot


