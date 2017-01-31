package org.chilternquizleague.maintain.season

import angulate2.std._

import scala.scalajs.js
import js.JSConverters._
import org.chilternquizleague.util.UUID
import angulate2.router.Router
import org.chilternquizleague.maintain.component.ListComponent
import org.chilternquizleague.util.UUID
import org.chilternquizleague.maintain.model._
import angulate2.ext.classModeScala

@Component(
  selector = "ql-team-list",
  template = """
  <div>
    <h2>Seasons</h2>
    <div *ngFor="let item of items">
      <a routerLink="/season/{{item.id}}" md-button>{{item.name}}</a>
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
class SeasonListComponent (
    override val service:SeasonService,
    override val router: Router) 
   extends ListComponent[Season] with OnInit with SeasonNames{
      
}