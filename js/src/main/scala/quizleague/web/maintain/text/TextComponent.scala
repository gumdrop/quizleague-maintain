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
      <div >
        <md-input-container>
        <textarea mdInput mdTextareaAutosize placeholder="Text" 
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
  
  override def cancel():Unit = location.back()
}