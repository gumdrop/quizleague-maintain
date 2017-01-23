package org.chilternquizleague.maintain.user

import angulate2.std._
import angulate2.router.ActivatedRoute
import org.chilternquizleague.maintain.model._
import angulate2.common.Location
import org.chilternquizleague.maintain.component._
import scalajs.js
import angulate2.ext.classModeScala
import TemplateElements._

@Component(
  selector = "ql-user",
  template = s"""
  <div>
    <h2>User Detail</h2>
    <form #fm="ngForm" (submit)="save()" >
      <div fxLayout="column">
        <md-input placeholder="Name" type="text" id="name"
             required
             [(ngModel)]="item.name" name="name">
        </md-input>
        <md-input placeholder="Email" type="email" id="email" required
             [(ngModel)]="item.email" name="email">
        </md-input>
        $chbxRetired 
     </div>
     $formButtons
    </form>
  </div>
  """    
)
@classModeScala
class UserComponent(
    override val service:UserService,
    override val route: ActivatedRoute,
    override val location:Location) extends ItemComponent[User] 