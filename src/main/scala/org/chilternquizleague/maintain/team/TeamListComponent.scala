package org.chilternquizleague.maintain.team

import angulate2.std._

import scala.scalajs.js
import js.JSConverters._
import org.chilternquizleague.util.UUID
import angulate2.router.Router
import org.chilternquizleague.maintain.component.ListComponent
import org.chilternquizleague.util.UUID
import org.chilternquizleague.maintain.model._

@Component(
  selector = "ql-venue-list",
  template = """
  <div>
    <h2>Teams</h2>
      <button md-button (click)="addNew()">Add</button>
    <div *ngFor="let item of items">
      <a routerLink="/team/{{item.id}}" md-button>{{item.name}}</a>
    </div>
  </div>
  """    
)
class TeamListComponent (
    override val service:TeamService,
    override val router: Router) 
   extends ListComponent[Team] with OnInit{
  
   override def instance() = { 
     val id = UUID.randomUUID.toString;
     (id, Team(id, "","",null))
   }
  
   
}