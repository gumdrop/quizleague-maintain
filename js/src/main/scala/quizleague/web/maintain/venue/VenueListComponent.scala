package quizleague.web.maintain.venue

import angulate2.std._
import quizleague.web.model.Venue
import quizleague.web.maintain.component.TemplateElements._
import scala.scalajs.js
import js.JSConverters._
import quizleague.web.util.UUID
import angulate2.router.Router
import quizleague.web.maintain.component.ListComponent
import quizleague.web.util.UUID
import quizleague.web.model.Venue
import angulate2.ext.classModeScala
import quizleague.web.names.VenueNames

@Component(
  selector = "ql-venue-list",
  template = s"""
  <div>
    <h2>Venues</h2>
    <div *ngFor="let item of items">
      <a routerLink="/venue/{{item.id}}" md-button>{{item.name}}</a>
    </div>
$addFAB
  </div>
  """    
)
@classModeScala
class VenueListComponent (
    override val service:VenueService,
    override val router: Router) 
   extends ListComponent[Venue] with OnInit with VenueNames{
  
}