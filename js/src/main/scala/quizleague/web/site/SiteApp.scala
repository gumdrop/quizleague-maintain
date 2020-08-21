package quizleague.web.site

import scalajs.js
import js.Dynamic.literal
import js.JSConverters._
import com.felstar.scalajs.vue._
import java.time.format.{DateTimeFormatter, DateTimeFormatterBuilder}

import quill.VueQuillEditor
import quizleague.web.util.rx._
import rxscalajs.Observable
import showdown.VueShowdown

import scala.scalajs.js.annotation.JSExport
import scala.scalajs.js.annotation.JSExportTopLevel


@JSExportTopLevel("Site")
object SiteApp{

  val dateFormatter = new DateTimeFormatterBuilder()
    .append(DateTimeFormatter.ISO_LOCAL_DATE)
    .append(DateTimeFormatter.ISO_LOCAL_TIME)
    .append(DateTimeFormatter.ISO_LOCAL_DATE_TIME)
    .toFormatter

  @JSExport
  def main():Unit = {
    Vue.use(VueQuillEditor)
    Vue.use(VueShowdown, showdown.defaultOptions)
    Vue.filter("date", (date:String, format:String) => DateTimeFormatter.ofPattern(format).format(DateTimeFormatter.ISO_LOCAL_DATE.parse(date)))
    Vue.filter("time", (time:String, format:String) => DateTimeFormatter.ofPattern(format).format(DateTimeFormatter.ISO_LOCAL_TIME.parse(time)))
    Vue.filter("datetime", (datetime:String, format:String) => DateTimeFormatter.ofPattern(format).format(DateTimeFormatter.ISO_LOCAL_DATE_TIME.parse(datetime)))
    Vue.filter("combine", (obs:js.Array[RefObservable[Any]]) => Observable.combineLatest(obs.map(_.obs)).map(_.toJSArray))
    Vue.filter("wrap", (obj:js.Any) => Observable.just(obj))
  new Vue(
        literal(el="#app",
        router = Router(SiteModule(), scrollBehavior = () => js.Dynamic.literal(x=0,y=0)
        ),
          vuetify = new Vuetify()
      )
    )

  }
} 

  
