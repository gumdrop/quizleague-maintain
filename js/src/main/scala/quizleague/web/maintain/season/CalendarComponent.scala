package quizleague.web.maintain.season

import angulate2.std._
import angulate2.router.ActivatedRoute
import angulate2.common.Location
import quizleague.web.maintain.component.ItemComponent
import quizleague.web.maintain.component._
import quizleague.web.model._
import scala.scalajs.js
import js.JSConverters._
import quizleague.web.maintain.venue.VenueService
import angulate2.ext.classModeScala
import TemplateElements._
import quizleague.web.maintain.text.TextService
import angulate2.router.Router
import js.Dynamic.{ global => g }
import quizleague.web.maintain.text.TextEditMixin
import quizleague.web.util.Logging
import quizleague.web.model.CompetitionType
import quizleague.web.maintain.competition.CompetitionService

@Component(
  selector = "ql-season",
  template = s"""
  <div>
    <h2>Calendar {{item.startYear}}/{{item.endYear}}</h2>
    <form #fm="ngForm" (submit)="save()">
      <div fxLayout="column">
        <button md-icon-button (click)="addEvent()" type="button"><md-icon>add</md-icon></button>
        <div *ngFor="let event of items;let i = index" fxLayout="row">
          <div fxLayout="column">
            <button md-icon-button (click)="deleteEvent(event)" type="button"><md-icon>delete</md-icon></button>
          </div>  
          <div fxLayout="column">
          <md-input-container>        
            <input mdInput placeholder="Description" type="text" 
            required
               [(ngModel)]="event.description" name="description{{i}}">
          </md-input-container>

          <div fxLayout="row">
            <md-input-container>        
              <input mdInput placeholder="Date" type="date"
                 required
                 [(ngModel)]="event.date" name="date{{i}}">
            </md-input-container>
            <md-input-container>        
              <input mdInput placeholder="Time" type="time"
                 required
                 [(ngModel)]="event.time" name="time{{i}}">
            </md-input-container>
            <md-input-container>        
              <input mdInput placeholder="Duration" type="number" step="0.1"
                 required
                 [(ngModel)]="event.duration" name="duration{{i}}">
            </md-input-container>
          </div>
            <md-select placeholder="Venue" name="venue{{i}}" [(ngModel)]="event.venue" required >
              <md-option *ngFor="let venue of venues" [value]="venue" >
                {{venue.name}}
              </md-option>
            </md-select>
        </div>
        </div>
      </div>
     $formButtons
    </form>
  </div>
  """    
)
@classModeScala
class CalendarComponent(
    override val service:SeasonService,
    override val route: ActivatedRoute,
    override val location:Location,
    val venueService:VenueService)
    extends ItemComponent[Season] with Logging{
  
    var items:js.Array[CalendarEvent] = _
    var venues:js.Array[Venue] = _
  
    override def init() = {
      loadItem().subscribe(x=> {items = x.calendar; item = x})
      initVenues()
    }
    
    private def initVenues() = venueService.list.subscribe(venues = _)
    
    def addEvent() = item.calendar += CalendarEvent(null,null,null,0,null)
    def deleteEvent(event:CalendarEvent) = item.calendar -= event
    
    override def save() = {service.cache(item);location.back()}
    override def cancel() = location.back()
    
}
    