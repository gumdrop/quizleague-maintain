package quizleague.web.maintain.globaltext

import angulate2.std._
import quizleague.web.maintain.component.TemplateElements._
import quizleague.web.model._
import scala.scalajs.js
import angulate2.router.Router
import quizleague.web.maintain.component.ListComponent


import angulate2.ext.classModeScala

@Component(
  selector = "ql-globaltext-list",
  template = s"""
  <div>
    <h2>Global Text</h2>
    <div *ngFor="let item of items">
      <a routerLink="/globalText/{{item.id}}" md-button>{{item.name}}</a>
    </div>
$addFAB
  </div>
  """    
)
@classModeScala
class GlobalTextListComponent (
    override val service:GlobalTextService,
    override val router: Router) 
   extends ListComponent[GlobalText] with OnInit with GlobalTextNames{
  
}