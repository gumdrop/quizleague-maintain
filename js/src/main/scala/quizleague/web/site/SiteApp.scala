package quizleague.web.site
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
import java.time.DateTimeUtils
import org.threeten.bp.format.DateTimeFormatter
import quizleague.web.util.rx._
import rxscalajs.Observable

@JSExportAll
object SiteApp{
  
 
  def main(args:Array[String]):Unit = {


  Vue.filter("date", (date:String, format:String) => DateTimeFormatter.ofPattern(format).format(DateTimeFormatter.ISO_LOCAL_DATE.parse(date)))
  Vue.filter("combine", (obs:js.Array[RefObservable[Any]]) => Observable.combineLatest(obs.map(_.obs)).map(_.toJSArray))
  Vue.filter("wrap", (obj:js.Any) => Observable.just(obj))
  
  val demo = new Vue(
        literal(el="#app",
          router = Router(SiteModule())
      )
    )

  }
} 

  
