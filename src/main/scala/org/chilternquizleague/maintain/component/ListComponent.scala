package org.chilternquizleague.maintain.component

import angulate2.core.OnInit
import scala.scalajs.js
import js.JSConverters._
import org.chilternquizleague.util.UUID
import org.chilternquizleague.maintain.service.EntityService
import angulate2.router.Router
import js.annotation.ScalaJSDefined
import scala.scalajs.js.annotation.JSExport
import scala.scalajs.js.annotation.JSExport


trait ListComponent[T] extends OnInit{
  
  val service:EntityService[T]
  val router:Router
  
  @JSExport
  var items:js.Array[T] = _
  
  @JSExport
  def addNew():Unit = {
    for(
      (id,item) <-Some(instance())
    )
    yield{
      service.put(id,item)
      router.navigateTo(s"/${service.name}",id)
    }
  }
  
  @JSExport
  override def ngOnInit() = service.list.subscribe(this.items = _)
  
  def instance():(String,T)
  
  
}

