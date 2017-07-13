package quizleague.web.maintain.text

import angulate2.std._
import angulate2.router.ActivatedRoute
import quizleague.web.model._
import angulate2.common.Location
import quizleague.web.maintain.component._
import scalajs.js
import angulate2.ext.classModeScala
import TemplateElements._

@Component(
  template = s"""
  <div>
    <h2>Text Detail</h2>
    <form #fm="ngForm" (submit)="save()" >
      <div fxLayout="column">
      <md-input-container>
        <input mdInput placeholder="Mime Type" [(ngModel)]="item.mimeType" name="mimeType">
      </md-input-container>
        <md-input-container>
        <textarea mdInput mdTextareaAutosize placeholder="Text" mdAutosizeMinRows="10" mdAutosizeMaxRows="40"
             required
             [(ngModel)]="item.text" name="text"></textarea>
        </md-input-container>
     </div>
     $formButtons
    </form>
  </div>
  """ ,
  styles = @@@("textarea{width:90%;}")
)
@classModeScala
class TextComponent(
    override val service:TextService,
    override val route: ActivatedRoute,
    override val location:Location) extends ItemComponent[Text]{
  
  override def cancel():Unit = location.back()
}