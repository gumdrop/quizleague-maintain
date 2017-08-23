package quizleague.web.site.competition

import angulate2.ext.classModeScala
import angulate2.router.ActivatedRoute
import angulate2.std._
import quizleague.web.site.common.{ MenuComponent, SectionComponent, SideMenuService, TitleService, TitledComponent }
import quizleague.web.site.global.ApplicationContextService
import quizleague.web.site.common.ComponentUtils
import ComponentUtils._

@Component(
  template = s"""
  <div *ngIf="itemObs | async as item; else loading" fxLayout="column" fxLayoutGap="5px">
    <ql-named-text [name]="textName"></ql-named-text>
    <ql-text [textId]="item.text.id"></ql-text>
    <md-card>
      <md-card-title>League Table</md-card-title>
      <md-card-content>
        <ql-league-table *ngFor="let table of item.tables" [ref]="table"></ql-league-table>
      </md-card-content>
    </md-card>
    <md-card>
      <md-card-title>Results</md-card-title>
      <md-card-subtitle>Latest results</md-card-subtitle>
      <md-card-content>
        <div *ngFor="let results of latestResults | async">
          <div>{{(results.fixtures | async)?.date | date:"d MMM yyyy"}}</div>
          <ql-results-simple [list]="results.results" ></ql-results-simple>
        </div>
      </md-card-content>
      <md-card-actions>
        <a md-button routerLink="results">Show All</a>
      </md-card-actions>
    </md-card>
    <md-card>
      <md-card-title>Fixtures</md-card-title>
      <md-card-subtitle>Next fixtures</md-card-subtitle>
      <md-card-content>
        <div *ngFor="let fixtures of nextFixtures | async">
          <div>{{fixtures.date | date:"d MMM yyyy"}}</div>
          <div>{{now}}</div>
          <ql-fixtures-simple [list]="fixtures.fixtures" ></ql-fixtures-simple>
        </div>
      </md-card-content>
      <md-card-actions>
        <a md-button routerLink="fixtures">Show All</a>
      </md-card-actions>
    </md-card>
  </div>
  $loadingTemplate
  """)
@classModeScala
class LeagueCompetitionComponent(
  route: ActivatedRoute,
  service: CompetitionService,
  viewService: CompetitionViewService,
  applicationContextService: ApplicationContextService,
  titleService: TitleService,
  sideMenuService: SideMenuService)
    extends BaseCompetitionComponent(
      route,
      service,
      viewService,
      applicationContextService,
      titleService,
      sideMenuService
    )
    with TeamCompetitionComponent{
  
    override val textName:String = "league-comp"
}

   
