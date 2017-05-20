package quizleague.web.site.venue

import angulate2.std._
import quizleague.web.site.common._
import angulate2.ext.classModeScala
import angulate2.router.ActivatedRoute
import quizleague.web.model.Venue


@Component(
    template = """
 <div *ngIf="itemObs | async as item; else loading">
     <md-card>
      <img md-card-img-sm src="{{item.imageURL}}">
      <md-card-content>

         <div>email : <a href="mailto:{{item.email}}">{{item.email}}</a></div>
         <div>website : <a href="{{item.website}}" target="_blank">{{item.website}}</a></div>
         <div>phone : {{item.phone}}</div>
      </md-card-content>
    </md-card>
  </div>
  <ng-template #loading>Loading...</ng-template>

"""
)
@classModeScala
class VenueComponent(
    route:ActivatedRoute,
    service:VenueService,
    override val titleService:TitleService,
    override val sideMenuService:SideMenuService) extends SectionComponent with MenuComponent with TitledComponent{    

  val itemObs = route.params.switchMap( (params,i) => service.get(params("id")))
  
  itemObs.subscribe(v => setTitle(v.name))
}

@Component(
    template = """
  <ql-section-title>
     <span *ngIf="itemObs | async as item; else loading">
      {{item.name}}
    </span>
    <ng-template #loading>Loading...</ng-template>
  </ql-section-title>
"""
)
class VenueTitleComponent(
        route:ActivatedRoute,
    service:VenueService
    ){   
  
  val itemObs = route.params.switchMap( (params,i) => service.get(params("id")))
}

@Component(
  template = s"""
  <div *ngFor="let item of items | async">
    <a routerLink="/venue/{{item.id}}"  md-button routerLinkActive="active" >{{item.name}}</a>
  </div>
  """    
)
class VenueMenuComponent(service:VenueService){
  
  val items = service.list()

}