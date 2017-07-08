package quizleague.web.maintain.globaltext

import angulate2.std._
import angulate2.router.ActivatedRoute
import quizleague.web.model._
import angulate2.common.Location
import quizleague.web.maintain.component._
import scalajs.js
import angulate2.ext.classModeScala
import TemplateElements._
import angulate2.router.Router
import quizleague.web.util.Logging

@Component(
  template = s"""
  <div>
    <h2>Global Text Detail</h2>
    <form #fm="ngForm" (submit)="save()" >
      <div fxLayout="column">
        <md-input-container>
        <input mdInput placeholder="Name" type="text" id="name"
             required
             [(ngModel)]="item.name" name="name">
        </md-input-container>
        <div *ngFor="let text of item.text;let i = index" fxLayout="row">
         <md-input-container>
          <input mdInput placeholder="Entry Name" type="text"
             required
             [(ngModel)]="text.name" name="textName{{i}}">
          </md-input-container>
          <button (click)="editText(text)" md-button type="button" >Edit text...</button>
        </div>
        $chbxRetired 
     </div>
     $formButtons
    </form>
    $addFAB
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
      router.navigateTo("/maintain/text", text.text.id)
    }
    
    def addNew() = {
      log(item, "before add")
      
      val text = service.entryInstance()
      item.text.push(text)
      
      log(item, "after add")
    }
}