package org.chilternquizleague.maintain.team

import angulate2.std._

import scala.scalajs.js
import js.JSConverters._
import org.chilternquizleague.web.util.UUID
import angulate2.router.Router
import org.chilternquizleague.maintain.component.ListComponent
import org.chilternquizleague.web.util.UUID
import org.chilternquizleague.web.model._
import angulate2.ext.classModeScala

@Component(
  selector = "ql-team-list",
  template = """
  <div>
    <h2>Teams</h2>
    <div *ngFor="let item of items">
      <a routerLink="/team/{{item.id}}" md-button>{{item.name}}</a>
    </div>
    <div style="position:absolute;right:1em;bottom:5em;">
      <button md-fab (click)="addNew()">
          <md-icon class="md-24">add</md-icon>
      </button>
    </div>
  </div>
  """    
)
@classModeScala
class TeamListComponent (
    override val service:TeamService,
    override val router: Router) 
   extends ListComponent[Team] with OnInit with TeamNames{
      
}