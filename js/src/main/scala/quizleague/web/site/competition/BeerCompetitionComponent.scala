package quizleague.web.site.competition

import angulate2.ext.classModeScala
import angulate2.router.ActivatedRoute
import angulate2.std._
import quizleague.web.site.common.{ MenuComponent, SectionComponent, SideMenuService, TitleService, TitledComponent }
import quizleague.web.site.global.ApplicationContextService

@Component(
  template = """
  <div *ngIf="itemObs | async as item; else loading" fxLayout="column" fxLayoutGap="5px">
    <ql-named-text [name]="textName"></ql-named-text>
    <ql-text [textId]="item.text.id"></ql-text>
    <md-card>
      <md-card-title>League Table</md-card-title>
      <md-card-content>
        <ql-league-table *ngFor="let table of item.tables" [table]="table"></ql-league-table>
      </md-card-content>
    </md-card>
    <md-card>
      <md-card-title>Results</md-card-title>
      <md-card-subtitle>Latest results</md-card-subtitle>
      <md-card-content>
        <div *ngFor="let results of latestResults(item.results)">
          <div>{{results.fixtures.date | date:"d MMM yyyy"}}</div>
          <ql-results-simple [results]="results.results" ></ql-results-simple>
        </div>
      </md-card-content>
      <md-card-actions>
        <a md-button routerLink="results">Show All</a>
      </md-card-actions>
    </md-card>
  </div>
  <ng-template #loading>Loading...</ng-template>
  """)
@classModeScala
class BeerCompetitionComponent(
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
  
    override val textName:String = "beer_front_page"
}

   
