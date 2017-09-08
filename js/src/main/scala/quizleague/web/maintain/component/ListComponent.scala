package quizleague.web.maintain.component

import angulate2.core.OnInit
import scala.scalajs.js
import js.JSConverters._
import quizleague.web.util.UUID
import quizleague.web.service.EntityService
import angulate2.router.Router
import js.annotation.ScalaJSDefined
import scala.scalajs.js.annotation.JSExport
import js.Dynamic.{ global => g }
import quizleague.web.service.GetService
import quizleague.web.service.PutService
import quizleague.web.names.ComponentNames


trait ListComponent[T] extends OnInit{
  this:ComponentNames =>
  val service:GetService[T] with PutService[T]
  val router:Router
  
  @JSExport
  var items:js.Array[T] = _
  
  @JSExport
  def addNew():Unit = {
    router.navigateTo(s"/$typeName",service.getId(service.instance()))
  }
  
  @JSExport
  override def ngOnInit() = service.list.subscribe(i => this.items = i.sort(sortit _))
  
  def sortit(i1:T,i2:T):Int = 0
  
}

