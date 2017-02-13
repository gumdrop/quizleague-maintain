package org.chilternquizleague.web.maintain.text

import org.chilternquizleague.web.model.Text
import angulate2.std.Input
import angulate2.std.Component

@Component(
  selector = "ql-text-editor",
  template = s"""
  <md-textarea placeholder="Text" 
       [required]="required"
       [(ngModel)]="text" name="text">
  </md-textarea>
  """    
)
class TextEditor {
  
  @Input
  val text:Text = null
  
  @Input
  val required:Boolean = false
}