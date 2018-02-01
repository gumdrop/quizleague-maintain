package quizleague.web.useredit
import scala.scalajs.js
import js.Dynamic._
import scala.scalajs.js.JSConverters._
import scala.scalajs.js.annotation.JSExport
import scala.scalajs.js.annotation.JSExportTopLevel

import org.threeten.bp.format.DateTimeFormatter

import com.felstar.scalajs.vue._

import quill.VueQuillEditor
import quizleague.web.util.rx._
import rxscalajs.Observable

@JSExportTopLevel("UserEdit")
object UserEditApp{
  
  @JSExport
  def main():Unit = {
  Vue.use(VueQuillEditor)
  Vue.filter("date", (date:String, format:String) => DateTimeFormatter.ofPattern(format).format(DateTimeFormatter.ISO_LOCAL_DATE.parse(date)))
  Vue.filter("combine", (obs:js.Array[RefObservable[Any]]) => Observable.combineLatest(obs.map(_.obs)).map(_.toJSArray))
  Vue.filter("wrap", (obj:js.Any) => Observable.just(obj))
  
  new Vue(
        literal(el="#user-edit-app",
        router = Router(UserEditAppModule())
      )
    )

  }
} 

  
