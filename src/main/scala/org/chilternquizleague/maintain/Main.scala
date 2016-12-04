package org.chilternquizleague.maintain

import angulate2.std._
import angulate2.router.{Route, RouterModule}
import angulate2.platformBrowser.BrowserModule
import angular.material.MaterialModule
import angulate2.forms.FormsModule
import org.chilternquizleague.maintain.venue.VenueRoutesModule


import scala.scalajs.js
import org.chilternquizleague.maintain.venue.VenuesModule



@NgModule(
  imports = @@[BrowserModule,MaterialModule,VenueRoutesModule] :+
   RouterModule.forRoot(js.Array(
    Route(
      path = "",
      component = %%[RootComponent]
  ))),
  declarations = @@[AppComponent,RootComponent],
  bootstrap = @@[AppComponent],
  providers = @@[EntityService]
)
class AppModule {

}

@Component(
  selector = "ql-app",
  template = """
  <div>
   <md-toolbar color='primary'>

      <button md-icon-button (click)="sidenav.toggle()">
        <i class='material-icons app-toolbar-menu'>menu</i>
      </button>
       Chiltern Quiz League


    </md-toolbar>
    <md-sidenav-layout>
      <md-sidenav #sidenav mode="side" opened="true">
        <a routerLink="/venue" md-button >Venues</a>
        
      </md-sidenav>
      <router-outlet></router-outlet>
    </md-sidenav-layout>
  </div>
  """
)
class AppComponent(entityService:EntityService) {
	var text = "No Text Yet"
	
	def changeText() = text = "Some Text Now"
}

@Component(
  selector = "ql-root",
  template = """
  <div>
  Hello World
  </div>
  """
)
class RootComponent() 