package quizleague.web.maintain.season

import angulate2.std._

import scala.scalajs.js
import js.JSConverters._
import quizleague.web.util.UUID
import angulate2.router.Router
import quizleague.web.maintain.component.ListComponent
import quizleague.web.util.UUID
import quizleague.web.model._
import angulate2.ext.classModeScala
import quizleague.web.maintain.component.TemplateElements._
import quizleague.web.names.SeasonNames

@Component(
  selector = "ql-team-list",
  template = s"""
  <div>
    <h2>Seasons</h2>
    <div *ngFor="let item of items">
      <a routerLink="{{item.id}}" md-button>{{item.startYear}}/{{item.endYear}}</a>
    </div>
    $addFAB
  </div>
  """    
)
@classModeScala
class SeasonListComponent (
    override val service:SeasonService,
    override val router: Router) 
   extends ListComponent[Season] with OnInit with SeasonNames{
      
   override def sortit(s1:Season, s2:Season) = s1.startYear - s2.startYear
   
}