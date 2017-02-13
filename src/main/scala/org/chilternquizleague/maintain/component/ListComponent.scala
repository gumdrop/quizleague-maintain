package org.chilternquizleague.maintain.component

import angulate2.core.OnInit
import scala.scalajs.js
import js.JSConverters._
import org.chilternquizleague.web.util.UUID
import org.chilternquizleague.web.service.EntityService
import angulate2.router.Router
import js.annotation.ScalaJSDefined
import scala.scalajs.js.annotation.JSExport
import js.Dynamic.{ global => g }


trait ListComponent[T] extends OnInit{
  this:ComponentNames =>
  val service:EntityService[T]
  val router:Router
  
  @JSExport
  var items:js.Array[T] = _
  
  @JSExport
  def addNew():Unit = {
    router.navigateTo(s"/$typeName",service.getId(service.instance()))
  }
  
  @JSExport
  override def ngOnInit() = service.list.subscribe(this.items = _)
  
  
}

