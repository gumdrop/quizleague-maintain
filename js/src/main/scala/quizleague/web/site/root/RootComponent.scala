package quizleague.web.site.root

import scala.scalajs.js
import js.timers._

import angular.core.BrowserAnimationsModule
import angular.flexlayout.FlexLayoutModule
import angular.material.MaterialModule
import angulate2.ext.classModeScala
import angulate2.ext.inMemoryWebApi.{ InMemoryBackendConfigArgs, InMemoryWebApiModule }
import angulate2.http.HttpModule
import angulate2.platformBrowser.BrowserModule
import angulate2.router.{ Route, Router }
import angulate2.std._
import quizleague.web.mock.MockData
import quizleague.web.site.common.{ CommonAppModule, NoMenuComponent, SectionComponent, SideMenuService, TitleService, TitledComponent }
import quizleague.web.site.competition.CompetitionModule
import quizleague.web.site.fixtures.FixturesModule
import quizleague.web.site.global.{ ApplicationContextModule, ApplicationContextService }
import quizleague.web.site.leaguetable.LeagueTableModule
import quizleague.web.site.results.ResultsModule
import quizleague.web.site.season.SeasonModule
import quizleague.web.site.team.TeamModule
import quizleague.web.site.text.TextModule
import quizleague.web.site.user.UserModule
import quizleague.web.site.venue.VenueModule
import quizleague.web.util.Logging
import rxjs.Observable
import quizleague.web.site.season.SeasonService
import angulate2.common.CommonModule
import quizleague.web.site.leaguetable.LeagueTableModule
import quizleague.web.site.season.SeasonService
import quizleague.web.site.results.ResultsComponentsModule
import quizleague.web.site.fixtures.FixturesComponentsModule
import java.time.LocalDate


@Component(
  template = """
  <div fxLayout="row" fxLayout.xs="column" fxLayoutGap="10px">
    <div>
    <md-tab-group dynamicHeight="true" [selectedIndex]="tabIndex" (click)="tabSelected()">
      <md-tab label="League Tables">
        <md-card>
          <ql-league-table *ngFor="let table of (league | async)?.tables" [table]="table"></ql-league-table>
        </md-card>
      </md-tab>
      <md-tab label="Latest Results">
        <md-card *ngFor="let res of results | async">
          <md-card-title>{{res.fixtures.parentDescription}} : {{res.fixtures.date | date:"dd MMM yyyy"}} - {{res.fixtures.description}}</md-card-title>
          <md-card-content>
            <ql-results-simple [results]="res.results" ></ql-results-simple>
          </md-card-content>
          
        </md-card>
        <ng-template #loading>Loading...</ng-template>  
      </md-tab>
      <md-tab label="Next Fixtures">
       <md-card *ngFor="let item of fixtures | async">
          <md-card-title>{{item.parentDescription}} : {{item.date | date:"dd MMM yyyy"}} - {{item.description}}</md-card-title>
          <md-card-content>
            <ql-fixtures-simple [fixtures]="item.fixtures" ></ql-fixtures-simple>
          </md-card-content>
    
        </md-card>
        <ng-template #loading>Loading...</ng-template>  
        
      </md-tab>
    </md-tab-group>
    </div>
      <div class="text_area">
        <ql-named-text name="front_page_main"></ql-named-text>
        <br>
        <ql-text [textId]="(currentSeason | async)?.text.id"></ql-text>
      </div>
  </div>
  """,
  styles = @@@("""
    .pad {padding-top:1em;}
  """,
  """.text_area{margin-left:1em;}""")
)
@classModeScala
class RootComponent(
    override val sideMenuService:SideMenuService,
    override val titleService:TitleService,
    val applicationContextService:ApplicationContextService,
    val seasonService:SeasonService) extends SectionComponent with NoMenuComponent with TitledComponent with OnInit{
  
  var tabIndex:Int = 0;
  val tabCount = 3;
  var intervalId:SetIntervalHandle = null
  
  setTitle("Home")
  val league = applicationContextService.get.switchMap((ac,i) => seasonService.getLeagueCompetition(ac.currentSeason))
  val results = applicationContextService.get
    .switchMap((ac,i) => seasonService.getResults(ac.currentSeason))
    .map((r,i) => r.take(1))
  val fixtures = applicationContextService.get
    .switchMap((ac,i) => seasonService.getFixtures(ac.currentSeason))
    .map((f,i) => f.filter(_.date >= LocalDate.now.toString))
    .map((f,i) => f.take(1))
    
  val currentSeason = applicationContextService.get.map((ac,i) => ac.currentSeason)
  
  def tabSelected(){
    clearInterval(intervalId)
  }
  
  override def ngOnInit() = {
    intervalId = setInterval(5000){tabIndex = if(tabIndex == tabCount - 1) 0 else tabIndex + 1}
  }
}

