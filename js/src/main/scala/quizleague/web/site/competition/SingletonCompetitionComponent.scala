package quizleague.web.site.competition

import angulate2.ext.classModeScala
import angulate2.router.ActivatedRoute
import angulate2.std.{ @@@, Component, Data, Injectable }
import quizleague.web.model.Event
import quizleague.web.site.common.{ SideMenuService, TitleService }
import quizleague.web.site.global.ApplicationContextService
import quizleague.web.site.common.ComponentUtils
import ComponentUtils._

@Component(
  template = s"""
  <div *ngIf="itemObs | async as item; else loading" fxLayout="column" fxLayoutGap="10px">
    <ql-named-text [name]="item.textName"></ql-named-text>
    <md-card>
      <div><b>This season's competition will take place at <a routerLink="/venue/{{item.event.venue.id}}">{{(item.event.venue | async)?.name}}</a> on {{item.event.date | date:"d MMMM yyyy"}} starting at {{formatTime(item.event) | date:"h:mm a"}}</b> </div>
      <br>
      <ql-text [textId]="item.text.id"></ql-text>
    </md-card> 
  </div>
  $loadingTemplate
  """,
  styles = @@@("""*{font-family:Roboto}"""))
@classModeScala
class SingletonCompetitionComponent(
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
    ){
  
    
    def formatTime(event:Event) = s"${event.date}T${event.time}"
}

   
