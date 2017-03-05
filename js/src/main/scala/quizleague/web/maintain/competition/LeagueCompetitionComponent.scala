package quizleague.web.maintain.competition

import angulate2.std._
import angulate2.router.ActivatedRoute
import angulate2.common.Location
import quizleague.web.maintain.component.ItemComponent
import quizleague.web.maintain.component._
import quizleague.web.model._
import scala.scalajs.js
import angulate2.ext.classModeScala
import TemplateElements._
import quizleague.web.maintain.text.TextService
import angulate2.router.Router
import js.Dynamic.{ global => g }
import angulate2.core.Input
import quizleague.web.maintain.text.TextEditMixin
import quizleague.web.util.Logging
import quizleague.web.maintain.text.TextEditMixin
import angulate2.router.Router


@Component(
  selector = "ql-league-competition",
  template = s"""
  <div>
    <h2>League Competition Detail</h2>
    <form #fm="ngForm" (submit)="save()">
      <div fxLayout="column">
        <md-input-container>
          <input mdInput placeholder="Name" type="text"
             required
             [(ngModel)]="item.name" name="name">
        </md-input-container>
        <md-input-container>
          <input mdInput placeholder="Start Time" type="time"
             required
             [(ngModel)]="item.startTime" name="startTime">
        </md-input-container>
        <md-input-container>        
          <input mdInput placeholder="Duration (hours)" type="number"
             required step=".1"
             [(ngModel)]="item.duration" name="duration">
        </md-input-container>
       </div>
      <div fxLayout="row"><button (click)="editText(item.text)" md-button type="button" >Edit Text...</button></div>
      <div fxLayout="row"><button (click)="fixtures(item)" md-button type="button" >Fixtures...</button></div>
      <div fxLayout="row"><button (click)="results(item)" md-button type="button" >Results...</button></div>
      <div fxLayout="row"><button (click)="tables(item)" md-button type="button" >Tables...</button></div>
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
   def fixtures(comp:LeagueCompetition) = {
     service.cache(item)
     router.navigateRelativeTo(route, "fixtures")
   }
   
   def results(comp:LeagueCompetition) = {
     service.cache(item)
     router.navigateRelativeTo(route, "results")
   }
   
   def tables(comp:LeagueCompetition) = {
     service.cache(item)
     router.navigateRelativeTo(route, "leaguetable")
   }
}
    