package quizleague.web.site.venue

import angulate2.std._
import quizleague.web.site.common._
import angulate2.ext.classModeScala
import angulate2.router.ActivatedRoute
import quizleague.web.model.Venue
import scala.collection.immutable.List
import scala.scalajs.js
import angular.platformBrowser.DomSanitizer
import quizleague.web.util.Logging
import quizleague.web.site.common.ComponentUtils
import ComponentUtils._

@Component(
    template = s"""
 <div *ngIf="itemObs | async as item; else loading">
     <md-card>
      <md-card-content>
         <div fxLayout="row" fxLayoutAlign="space-between start">
           <div fxLayout="column">
             <div [innerText]="item.address" ></div>
             <div class="map" fxHide.xs="true">
               <iframe [src]="embeddedUrl(item)" width="400" height="300" frameborder="0" style="border: 0"></iframe>
             </div>
             <div fxHide="true" fxShow.xs="true"><a [href]="linkUrl(item)" target="_blank">map</a></div>
             <div>email : <a href="mailto:{{item.email}}">{{item.email}}</a></div>
             <div>website : <a href="{{item.website}}" target="_blank">{{item.website}}</a></div>
             <div>phone : {{item.phone}}</div>
           </div>
           <div fxHide="true" fxShow.gt-sm="true">
              <img md-card-img-sm src="{{item.imageURL}}">
           </div>
         </div>
      </md-card-content>
    </md-card>
  </div>
  $loadingTemplate
""",
    styles = @@@("""
       .map{
          margin-top:.5em;
          margin-bottom:.5em;
        }
 """)
)
@classModeScala
class VenueComponent(
    route:ActivatedRoute,
    service:VenueService,
    sanitiser:DomSanitizer, 
    override val titleService:TitleService,
    override val sideMenuService:SideMenuService) extends SectionComponent with MenuComponent with TitledComponent{    

  val itemObs = route.params.switchMap( (params,i) => service.get(params("id")))
  
  itemObs.subscribe(v => setTitle(v.name))
  
  def embeddedUrl(venue:Venue) = sanitiser.bypassSecurityTrustResourceUrl(makeParts(venue).join(""))
  
  def linkUrl(venue:Venue) = sanitiser.bypassSecurityTrustResourceUrl(makeParts(venue).take(2).join(""))
    
  private def makeParts(venue:Venue) = {
    js.Array("https://maps.google.com/maps?&q=",js.URIUtils.encodeURIComponent(s"${venue.name} ${venue.address}".replaceAll("\\s", "+")), "&output=embed")
  } 
}

@Component(
    template = s"""
  <ql-section-title>
     <span *ngIf="itemObs | async as item; else loading">
      {{item.name}}
    </span>
    $loadingTemplate
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
  <div fxLayout="column" *ngFor="let item of items | async">
    <a fxFlexAlign="start" routerLink="/venue/{{item.id}}"  md-button routerLinkActive="active" >{{item.name}}</a>
  </div>
  """    
)
class VenueMenuComponent(service:VenueService){
  
  val items = service.list()

}