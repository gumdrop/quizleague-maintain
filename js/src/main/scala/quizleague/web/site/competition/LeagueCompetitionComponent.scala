package quizleague.web.site.competition

import angulate2.ext.classModeScala
import angulate2.router.ActivatedRoute
import angulate2.std._
import quizleague.web.site.common.{ MenuComponent, SectionComponent, SideMenuService, TitleService, TitledComponent }
import quizleague.web.site.global.ApplicationContextService

@Component(
  template = s"""
  <div *ngIf="itemObs | async as item; else loading" fxLayout="column" fxLayoutGap="5px">
    <ql-text [textId]="item.text.id"></ql-text>
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
    <md-card>
      <md-card-title>Fixtures</md-card-title>
      <md-card-subtitle>Next fixtures</md-card-subtitle>
      <md-card-content>
        <div *ngFor="let fixtures of nextFixtures(item.fixtures)">
          <div>{{fixtures.date | date:"d MMM yyyy"}}</div>
          <div>{{now}}</div>
          <ql-fixtures-simple [fixtures]="fixtures.fixtures" ></ql-fixtures-simple>
        </div>
      </md-card-content>
      <md-card-actions>
        <a md-button routerLink="fixtures">Show All</a>
      </md-card-actions>
    </md-card>
  </div>
  <ng-template #loading>Loading...</ng-template>
  """)
@classModeScala
class LeagueCompetitionComponent(
  route: ActivatedRoute,
  service: CompetitionService,
  viewService: CompetitionViewService,
  applicationContextService: ApplicationContextService,
  override val titleService: TitleService,
  override val sideMenuService: SideMenuService)
    extends SectionComponent
    with MenuComponent
    with TitledComponent
    with TeamCompetitionComponent {
  
  val itemObs = route.params.switchMap((params, i) => service.get(params("id"))(4))

  itemObs.subscribe(t => setTitle(t.name))

}

   
