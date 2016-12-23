package org.chilternquizleague.maintain.component

import angulate2.core.OnInit
import scala.scalajs.js
import js.JSConverters._
import org.chilternquizleague.util.UUID
import org.chilternquizleague.maintain.service.EntityService
import angulate2.router.Router
import js.annotation.ScalaJSDefined
import scala.scalajs.js.annotation.JSExport
import js.Dynamic.{ global => g }




trait ListComponent[T] extends OnInit{
  this:ComponentNames with IdStuff[T] =>
  val service:EntityService[T]
  val router:Router
  
  @JSExport
  var items:js.Array[T] = _
  
  @JSExport
  def addNew():Unit = {
    
    val item = instance()
    
    service.add(item)
    router.navigateTo(s"/$typeName",getId(item))
  }
  
  @JSExport
  override def ngOnInit() = service.list.subscribe(this.items = _)
  
  def instance():T
  
  
}

