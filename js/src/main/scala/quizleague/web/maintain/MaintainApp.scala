package quizleague.web.maintain
import org.scalajs.dom

import scalajs.js.annotation.JSExportAll
import scala.scalajs.concurrent.JSExecutionContext.Implicits.runNow
import dom.ext.Ajax

import scalajs.js
import js.JSConverters._
import js.Dynamic.literal
import com.felstar.scalajs.vue._
import org.scalajs.dom.raw.HTMLElement

import js.annotation.JSName
import java.time.format.DateTimeFormatter

import quizleague.web.util.rx._
import rxscalajs.Observable
import quill.VueQuillEditor

import scala.scalajs.js.annotation.JSExport
import scala.scalajs.js.annotation.JSExportTopLevel
import firebase._
import quizleague.web.store.Firestore
import showdown._

@JSExportTopLevel("Maintain")
object MaintainApp{
  
  @JSExport
  def main():Unit = {
  
  //set up firebase auth context
  Firestore.setAuthContext()
    
  Vue.use(VueQuillEditor)
  Vue.use(VueShowdown, showdown.defaultOptions)
  Vue.filter("date", (date:String, format:String) => DateTimeFormatter.ofPattern(format).format(DateTimeFormatter.ISO_LOCAL_DATE.parse(date)))
  Vue.filter("combine", (obs:js.Array[RefObservable[Any]]) => Observable.combineLatest(obs.map(_.obs)).map(_.toJSArray))
  Vue.filter("wrap", (obj:js.Any) => Observable.just(obj))
  
  new Vue(
        literal(el="#maintain-app",
        router = Router(MaintainAppModule()),
          vuetify = new Vuetify()
      )
    )

  }
} 

  
