package quizleague.web.site

import scalajs.js
import js.Dynamic.literal
import js.JSConverters._
import com.felstar.scalajs.vue._

import org.threeten.bp.format.DateTimeFormatter
import quizleague.web.util.rx._
import rxscalajs.Observable
import scala.scalajs.js.annotation.JSExport
import scala.scalajs.js.annotation.JSExportTopLevel


@JSExportTopLevel("Site")
object SiteApp{
  
  @JSExport
  def main():Unit = {
    Vue.filter("date", (date:String, format:String) => DateTimeFormatter.ofPattern(format).format(DateTimeFormatter.ISO_LOCAL_DATE.parse(date)))
    Vue.filter("combine", (obs:js.Array[RefObservable[Any]]) => Observable.combineLatest(obs.map(_.obs)).map(_.toJSArray))
    Vue.filter("wrap", (obj:js.Any) => Observable.just(obj))
    Vue.filter("refobs", (obs:Observable[js.Any]) => RefObservable(obs.hashCode.toString, () => obs))
  new Vue(
        literal(el="#app",
        router = Router(SiteModule())
      )
    )

  }
} 

  
