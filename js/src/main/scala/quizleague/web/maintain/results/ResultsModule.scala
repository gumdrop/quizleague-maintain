package quizleague.web.maintain.results

import angulate2.std._
import angular.material.MaterialModule
import angulate2.forms.FormsModule
import angulate2.router.{Route,RouterModule}

import scala.scalajs.js

import angulate2.http.Http
import angular.flexlayout.FlexLayoutModule
import quizleague.web.maintain.component.ComponentNames


import angulate2.common.CommonModule
import quizleague.web.service.results._

import angulate2.ext.classModeScala
import quizleague.web.maintain._

@NgModule(
  imports = @@[CommonModule,FormsModule,MaterialModule,RouterModule,FlexLayoutModule],
//  declarations = @@[SeasonComponent,SeasonListComponent],
  providers = @@[ResultsService]
   
)
class ResultsModule

//@Routes(
//  root = false,
//      Route(
//        path = "results/:id",
//        component = %%[SeasonComponent]
//      )
//)
//class ResultsRoutesModule 

trait ResultsNames extends ComponentNames{
  override val typeName = "results"
}

@Injectable
@classModeScala
class ResultsService(override val http: Http) extends ResultsGetService with ResultsPutService with ServiceRoot


