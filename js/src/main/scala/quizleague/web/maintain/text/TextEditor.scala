package quizleague.web.maintain.text

import quizleague.web.model.Text
import angulate2.std.Input
import angulate2.std.Component

@Component(
  selector = "ql-text-editor",
  template = s"""
  <md-input-container>
  <textarea placeholder="Text" mdInput
       [required]="required"
       [(ngModel)]="text" name="text">
  </textarea>
  <md-input-container>
  """    
)
class TextEditor {
  
  @Input
  val text:Text = null
  
  @Input
  val required:Boolean = false
}