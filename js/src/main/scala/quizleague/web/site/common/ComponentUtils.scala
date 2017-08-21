package quizleague.web.site.common

import scalajs.js
import quizleague.web.util.rx.{zip => zipit}
import quizleague.web.util.rx.RefObservable
import scala.scalajs.js.annotation.JSExport


trait ComponentUtils {
  @JSExport  
  def zip[T](list:js.Array[RefObservable[T]]) = zipit(list)
}

object ComponentUtils {
  val loadingTemplate = """<ng-template #loading><span><md-progress-spinner style="transform:scale(.25);" mode="indeterminate"></md-progress-spinner></span></ng-template>"""
  val loadingTemplateThin = """<ng-template #loading><span style="position:absolute;"><md-progress-spinner style="transform:scale(.25);" mode="indeterminate"></md-progress-spinner></span></ng-template>"""

}