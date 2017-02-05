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
  selector = "ql-league-competition",
  template = s"""
      <div fxLayout="column">
        <md-input-container>
          <input md-input placeholder="Name" type="text"
             required
             [(ngModel)]="item.name" name="name">
        </md-input-container>
        <md-input-container>
          <input md-input placeholder="Start Time" type="localtime"
             required
             [(ngModel)]="item.startTime" name="startTime">
        </md-input-container>
        <md-input-container>        
          <input md-input placeholder="Duration (hours)" type="number"
             required
             [(ngModel)]="item.duration" name="duration">
        </md-input-container>
     </div>
  """    
)
@classModeScala
class LeagueCompetitionComponent(
    val service:CompetitionService){
  
  @Input
  val item:LeagueCompetition = null

}
    