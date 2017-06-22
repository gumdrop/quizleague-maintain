package quizleague.web.site.calendar

import scala.scalajs.js.annotation.JSExport

import angulate2.ext.classModeScala
import angulate2.router.ActivatedRoute
import angulate2.std.{ @@@, Component, Data, Injectable }
import quizleague.web.site.common.{ NoMenuComponent, SectionComponent, SideMenuService, TitleService, TitledComponent }
import quizleague.web.util.Logging.log
import angulate2.core.Input

@Component(
  template = """
  <div *ngIf="itemObs | async as items ; else loading" fxLayout="column" fxLayoutGap="10px">
  <md-card *ngFor="let item of items">
     <md-card-title>{{item.date | date:"EEEE d MMMM yyyy"}}</md-card-title>
     <md-card-content>
      <div *ngFor="let event of item.events">
        <div [ngSwitch]="event.eventType">
          <ql-results-event *ngSwitchCase="'results'" [event]="event"></ql-results-event>
          <ql-fixtures-event *ngSwitchCase="'fixtures'" [event]="event"></ql-fixtures-event>
          <ql-calendar-event *ngSwitchCase="'calendar'" [event]="event"></ql-calendar-event>
          <ql-competition-event *ngSwitchCase="'competition'" [event]="event"></ql-competition-event>
        </div>
      </div>
     </md-card-content>
  </md-card>
 
  </div>
  <ng-template #loading>Loading...</ng-template>
""")
@classModeScala
class CalendarComponent(
    service: CalendarViewService,
    override val titleService: TitleService,
    override val sideMenuService: SideMenuService) extends SectionComponent with NoMenuComponent with TitledComponent {

  setTitle("Calendar")

  val itemObs = service.season.switchMap((s, i) => service.getEvents(s))

}

@Component(
  template = """
  <ql-section-title>
    Calendar <ql-season-select [currentSeason]="service.season | async" (onchange)="service.seasonChanged($event)"></ql-season-select>
  </ql-section-title>
""")
class CalendarTitleComponent(
  route: ActivatedRoute,
  val service: CalendarViewService)
  

trait EventComponent{
  
  @JSExport
  var event:EventWrapper = _
}
  
  
object PanelComponent {
  val buttonStyle = ".fixButPos{top:-12px;}"
}

import PanelComponent._

trait PanelComponent extends EventComponent{
  
  @JSExport
  var panelVisible:Boolean = false
  
  @JSExport
  def togglePanel() = panelVisible = !panelVisible 
}




@Component(
  selector = "ql-results-event",
  template = """
         <div fxLayout="column">
            <div fxLayout="row">
            <div><a routerLink="/competition/{{event.competition.id}}/{{event.competition.typeName}}">{{event.results.fixtures.parentDescription}}</a> {{event.results.fixtures.description}}</div>
            <button class="fixButPos" md-icon-button (click)="togglePanel()">
              <md-icon *ngIf="!panelVisible" class="md-24" mdTooltip="Show results">visibility</md-icon>
              <md-icon *ngIf="panelVisible" class="md-24" mdTooltip="Hide results">visibility_off</md-icon>
            </button>
            </div>  
          <div *ngIf="panelVisible"><ql-results-simple [results]="event.results.results"></ql-results-simple></div>
          </div>

""",
  styles = @@@(buttonStyle),
  inputs = @@@("event")
)
@classModeScala
class ResultsEventComponent extends PanelComponent

@Component(
  selector = "ql-fixtures-event",
  template = """
         <div fxLayout="column">
            <div fxLayout="row">
            <div><a routerLink="/competition/{{event.competition.id}}/{{event.competition.typeName}}">{{event.fixtures.parentDescription}}</a> {{event.fixtures.description}}</div>
            <button class="fixButPos" md-icon-button (click)="togglePanel()">
              <md-icon *ngIf="!panelVisible" class="md-24" mdTooltip="Show fixtures">visibility</md-icon>
              <md-icon *ngIf="panelVisible" class="md-24" mdTooltip="Hide fixtures">visibility_off</md-icon>
            </button>
            </div>  
          <div *ngIf="panelVisible"><ql-fixtures-simple [fixtures]="event.fixtures.fixtures"></ql-fixtures-simple></div>
          </div>

""",
  styles = @@@(buttonStyle),
  inputs = @@@("event")
)
@classModeScala
class FixturesEventComponent extends PanelComponent

@Component(
  selector = "ql-calendar-event",
  template = """
        <div><b>{{event.event.description}}</b>  {{event.event.time}}  Venue : <a routerLink="/venue/{{(event.event.venue | async).id}}">{{(event.event.venue | async).name}}</a></div>
""",
  inputs = @@@("event")
)
@classModeScala
class CalendarEventComponent extends EventComponent

@Component(
  selector = "ql-competition-event",
  template = """
        <div><a routerLink="/competition/{{event.competition.id}}/{{event.competition.typeName}}">{{event.competition.name}}</a>  {{event.event.time}}  Venue : <a routerLink="/venue/{{(event.event.venue | async).id}}">{{(event.event.venue | async).name}}</a></div>
""",
  inputs = @@@("event")
)
@classModeScala
class CompetitionEventComponent extends EventComponent



