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
import quizleague.web.maintain.season.SeasonService
import angulate2.router.Router
import js.Dynamic.{ global => g }
import angulate2.core.Input
import angulate2.router.Router
import rxjs.Observable


@Component(
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
        <md-select placeholder="Subsidiary Competition" name="subsidiary" [(ngModel)]="item.subsidiary"  >
          <md-option *ngFor="let subsidiary of competitions" [value]="subsidiary" >
            {{subsidiary.name}}
          </md-option>
        </md-select>
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
                                  override val router:Router,
                                  val seasonService:SeasonService) extends CompetitionComponent{
  
  var competitions:js.Array[Competition] = _
  
  def filterSubs(c:Competition) = {
    c match {
      case s:SubsidiaryLeagueCompetition => true
      case _ => false
    }
  }
  
  override def init() = {
    route.params
    .switchMap((params,i) => seasonService.get(params("seasonId")))
    .switchMap((s,i) => Observable.zip(s.competitions.map(_.obs):_*))
    .subscribe(c => competitions = c.filter(filterSubs _))
    super.init()}

}
    