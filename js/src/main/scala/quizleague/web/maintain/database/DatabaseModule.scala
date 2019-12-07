package quizleague.web.maintain.database

import quizleague.web.maintain._
import quizleague.web.core._
import org.scalajs.dom.raw.File
import org.scalajs.dom.raw.FileReader
import org.scalajs.dom.raw.UIEvent
import rxscalajs.dom.Request
import rxscalajs.Observable
import rxscalajs.subjects.ReplaySubject
import quizleague.web.util.Logging._


object DatabaseModule extends Module {
  override val routes = @@(
    RouteConfig(path = "/maintain/database", components = Map("default" -> DatabaseComponent)))

}


object DatabaseService{
  
  def upload(file:File) = {
     val reader = new FileReader()
     val ret = ReplaySubject[Any]()
     reader.onload = (event:UIEvent) => {
      
      log(reader.result)
       
      val request = Request(
        "/rest/entity/nesteddbupload",
        headers = Map("Content-Type" -> "application/json", "Accept-Content" -> "application/json"),
        data = reader.result.toString,
        method = "POST")
    
      Observable.ajax(request).subscribe(x => ret.next(x), e => ret.error(e))
       
     }
     reader.readAsText(file)
     ret
  }
}
