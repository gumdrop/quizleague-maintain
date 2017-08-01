package quizleague.web.site.root

import scala.scalajs.js

import org.threeten.bp.LocalDate

import angulate2.core.OnChanges.SimpleChanges
import angulate2.ext.classModeScala
import angulate2.std._
import scala.scalajs.js.timers._
import quizleague.web.model._
import quizleague.web.site.common.ComponentUtils
import quizleague.web.site.common.ComponentUtils.loadingTemplate
import quizleague.web.site.common.NoMenuComponent
import quizleague.web.site.common.SectionComponent
import quizleague.web.site.common.SideMenuService
import quizleague.web.site.common.TitleService
import quizleague.web.site.common.TitledComponent
import quizleague.web.site.global.ApplicationContextService
import quizleague.web.site.results.ResultsService
import quizleague.web.site.season.SeasonService
import rxjs.Observable


@Component(
  template = """
  <div fxLayout="row" fxLayout.xs="column" fxLayoutGap="10px">
    <div>
    <md-tab-group dynamicHeight="true" [selectedIndex]="tabIndex" (click)="tabSelected()">
      <md-tab label="League Tables">
        <ql-root-league-table [season]="currentSeason | async"></ql-root-league-table>
      </md-tab>
      <md-tab label="Latest Results">
        <ql-root-latest-results></ql-root-latest-results>
      </md-tab>
      <md-tab label="Next Fixtures">
        <ql-root-next-fixtures></ql-root-next-fixtures>
      </md-tab>
    </md-tab-group>
    </div>
      <div class="text_area">
        <ql-named-text name="front-page-main"></ql-named-text>
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
    val applicationContextService:ApplicationContextService) 
      extends SectionComponent 
      with NoMenuComponent 
      with TitledComponent 
      with ComponentUtils 
      with OnInit
      with OnDestroy{
  
  var tabIndex:Int = 0;
  val tabCount = 3;
  var intervalId:SetIntervalHandle = null
  
  setTitle("Home")

    
  val currentSeason = applicationContextService.get.switchMap((ac,i) => ac.currentSeason.obs)
  
  def tabSelected(){
    clearInterval(intervalId)
  }
  
  override def ngOnInit() = {
    intervalId = setInterval(5000){tabIndex = if(tabIndex == tabCount - 1) 0 else tabIndex + 1}
  }
  
  override def ngOnDestroy() = {
    clearInterval(intervalId)
  }
}

@Component(
  selector = "ql-root-league-table",
  template = """
        <md-card>
          <ql-league-table *ngFor="let table of (league | async)?.tables" [table]="table"></ql-league-table>
        </md-card>
 """
)
class LeagueTableComponent(
    applicationContextService:ApplicationContextService,
    seasonService:SeasonService) extends OnChanges{
  
  @Input
  var season:Season = null
  
  var league:Observable[Competition] = null
  
  override def ngOnChanges(changes: SimpleChanges) {
    league = if(season != null) seasonService.getLeagueCompetition(season) else null
  }

}

@Component(
  selector = "ql-root-latest-results",
  template = s"""
        <md-card *ngFor="let res of results | async">
          <md-card-title *ngIf="res.fixtures | async as fixtures">{{fixtures.parentDescription}} : {{fixtures.date | date:"dd MMM yyyy"}} - {{fixtures.description}}</md-card-title>
          <md-card-content>
            <ql-results-simple [list]="res.results" ></ql-results-simple>
          </md-card-content>
          
        </md-card>
        $loadingTemplate  
 """
)
class LatestResultsComponent(
    applicationContextService:ApplicationContextService,
    resultsService:ResultsService){
  val results = applicationContextService.get
    .switchMap((ac,i) => ac.currentSeason.obs)
    .switchMap((s,i) => resultsService.latestResults(s))
    .map((r,i) => r.take(1))
}

@Component(
  selector = "ql-root-next-fixtures",
  template = s"""
       <md-card *ngFor="let item of fixtures | async">
          <md-card-title>{{item.parentDescription}} : {{item.date | date:"dd MMM yyyy"}} - {{item.description}}</md-card-title>
          <md-card-content>
            <ql-fixtures-simple [list]="item.fixtures" ></ql-fixtures-simple>
          </md-card-content>
    
        </md-card>
        $loadingTemplate    
 """
)
class NextFixturesComponent(
    applicationContextService:ApplicationContextService,
    seasonService:SeasonService){
  val fixtures = applicationContextService.get
    .switchMap((ac,i) => ac.currentSeason.obs)
    .switchMap((s,i) => seasonService.getFixtures(s))
    .map((f,i) => f.filter(_.date >= LocalDate.now.toString))
    .map((f,i) => f.take(1))
}

