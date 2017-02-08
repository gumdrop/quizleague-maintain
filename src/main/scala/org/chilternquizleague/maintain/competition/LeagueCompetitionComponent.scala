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
import org.chilternquizleague.maintain.text.TextEditMixin
import org.chilternquizleague.util.Logging
import org.chilternquizleague.maintain.text.TextEditMixin
import angulate2.router.Router


@Component(
  selector = "ql-league-competition",
  template = s"""
  <div>
    <h2>League Competition Detail</h2>
    <form #fm="ngForm" (submit)="save()">
      <div fxLayout="column">
        <md-input-container>
          <input md-input placeholder="Name" type="text"
             required
             [(ngModel)]="item.name" name="name">
        </md-input-container>
        <md-input-container>
          <input md-input placeholder="Start Time" type="time"
             required
             [(ngModel)]="item.startTime" name="startTime">
        </md-input-container>
        <md-input-container>        
          <input md-input placeholder="Duration (hours)" type="number"
             required step=".1"
             [(ngModel)]="item.duration" name="duration">
        </md-input-container>
       </div>
      <div fxLayout="row"><button (click)="editText(item.text)" md-button type="button" >Edit Text...</button></div>
     $formButtons
    </form>
  </div>

  """    
)
@classModeScala
class LeagueCompetitionComponent( override val service:CompetitionService,
                                  override val location:Location,
                                  override val route:ActivatedRoute,
                                  override val router:Router) extends ItemComponent[Competition] with TextEditMixin[Competition] with Logging{
   
    override def save() = {service.cache(item);location.back()}

}
    