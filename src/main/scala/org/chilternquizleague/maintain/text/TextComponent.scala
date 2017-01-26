package org.chilternquizleague.maintain.text

import angulate2.std._
import angulate2.router.ActivatedRoute
import org.chilternquizleague.maintain.model._
import angulate2.common.Location
import org.chilternquizleague.maintain.component._
import scalajs.js
import angulate2.ext.classModeScala
import TemplateElements._

@Component(
  selector = "ql-text",
  template = s"""
  <div>
    <h2>Text Detail</h2>
    <form #fm="ngForm" (submit)="save()" >
      <div fxLayout="column" fxFlex="100">
        <md-textarea placeholder="Text" fxFill 
             required
             [(ngModel)]="item.text" name="text">
        </md-textarea>
     </div>
     $formButtons
    </form>
  </div>
  """    
)
@classModeScala
class TextComponent(
    override val service:TextService,
    override val route: ActivatedRoute,
    override val location:Location) extends ItemComponent[Text]{
  
  override def save() = {service.cache(item);location.back()}
}