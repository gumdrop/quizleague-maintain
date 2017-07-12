package quizleague.web.maintain.user

import angulate2.std._
import quizleague.web.maintain.component.TemplateElements._
import quizleague.web.model._
import scala.scalajs.js
import angulate2.router.Router
import quizleague.web.maintain.component.ListComponent


import angulate2.ext.classModeScala
import quizleague.web.names.UserNames

@Component(
  selector = "ql-user-list",
  template = s"""
  <div>
    <h2>Users</h2>
    <div *ngFor="let item of items">
      <a routerLink="{{item.id}}" md-button>{{item.name}}</a>
    </div>
$addFAB
  </div>
  """    
)
@classModeScala
class UserListComponent (
    override val service:UserService,
    override val router: Router) 
   extends ListComponent[User] with OnInit with UserNames{
  
}