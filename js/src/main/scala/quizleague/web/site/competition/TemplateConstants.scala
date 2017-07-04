package quizleague.web.site.competition

object TemplateConstants {
  val cupTemplate = """
  <div *ngIf="itemObs | async as item; else loading" fxLayout="column" fxLayoutGap="5px">
    <ql-named-text [name]="textName"></ql-named-text>
    <ql-text [textId]="item.text.id"></ql-text>
    <md-card>
      <md-card-title>Results</md-card-title>
      <md-card-subtitle>Latest results</md-card-subtitle>
      <md-card-content>
        <div *ngFor="let results of latestResults(itemObs) | async">
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
        <div *ngFor="let fixtures of nextFixtures(itemObs) | async">
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
  """
}