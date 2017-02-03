package org.chilternquizleague.maintain.competition

import angulate2.std._
import angulate2.router.ActivatedRoute
import angulate2.common.Location
import org.chilternquizleague.maintain.component.ItemComponent
import org.chilternquizleague.maintain.component._
import org.chilternquizleague.maintain.model._
import scala.scalajs.js
import angulate2.ext.classModeScala
import TemplateElements._
import org.chilternquizleague.maintain.text.TextService
import angulate2.router.Router
import js.Dynamic.{ global => g }
import angulate2.core.Input


@Component(
  selector = "ql-competition",
  template = s"""
  <div>
    <h2>{{item.name}}</h2>
    <form #fm="ngForm" (submit)="save()">
      <div fxLayout="column">
        <md-input placeholder="Start Year" type="number"
             required
             [(ngModel)]="item.startYear" name="startYear">
        </md-input>
        <md-input placeholder="End Year" type="number"
             required
             [(ngModel)]="item.endYear" name="endYear">
        </md-input>
        <div fxLayout="row"><button (click)="editText(item.text)" md-button type="button" >Edit Text...</button></div>
     </div>
     $formButtons
    </form>
  </div>
  """    
)
@classModeScala
class LeagueCompetitionComponent(
    val service:CompetitionService){
  
  @Input
  val item:LeagueCompetition = null

}
    