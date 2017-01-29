package org.chilternquizleague.maintain.globaltext

import angulate2.std._
import angulate2.router.ActivatedRoute
import org.chilternquizleague.maintain.model._
import angulate2.common.Location
import org.chilternquizleague.maintain.component._
import scalajs.js
import angulate2.ext.classModeScala
import TemplateElements._
import angulate2.router.Router
import org.chilternquizleague.util.Logging

@Component(
  selector = "ql-user",
  template = s"""
  <div>
    <h2>GlobalText Detail</h2>
    <form #fm="ngForm" (submit)="save()" >
      <div fxLayout="column">
        <md-input placeholder="Name" type="text" id="name"
             required
             [(ngModel)]="item.name" name="name">
        </md-input>
        <div *ngFor="let text of item.text" fxLayout="row"><button (click)="editText(text)" md-button type="button" >{{text.name}}</button></div>
        $chbxRetired 
     </div>
     $formButtons
    </form>
  </div>
  """    
)
@classModeScala
class GlobalTextComponent(
    override val service:GlobalTextService,
    override val route: ActivatedRoute,
    override val location:Location,
    val router:Router) extends ItemComponent[GlobalText] with Logging {
  
    def editText(text:TextEntry) = {
      service.cache(item)
      log(text, "Text Entry : ")
      router.navigateTo("/text", text.text.id)
  }
}