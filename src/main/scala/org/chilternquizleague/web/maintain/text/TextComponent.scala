package org.chilternquizleague.web.maintain.text

import angulate2.std._
import angulate2.router.ActivatedRoute
import org.chilternquizleague.web.model._
import angulate2.common.Location
import org.chilternquizleague.web.maintain.component._
import scalajs.js
import angulate2.ext.classModeScala
import TemplateElements._

@Component(
  selector = "ql-text",
  template = s"""
  <div>
    <h2>Text Detail</h2>
    <form #fm="ngForm" (submit)="save()" >
      <div fxFlexFill>
        <md-input-container>
        <textarea mdInput md-textarea-autosize minRows="10"  placeholder="Text" 
             required
             [(ngModel)]="item.text" name="text"></textarea>
        </md-input-container>
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
  override def cancel():Unit = location.back()
}