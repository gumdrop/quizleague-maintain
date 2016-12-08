package org.chilternquizleague.maintain.component

import angulate2.core.OnInit
import scala.scalajs.js
import js.JSConverters._
import org.chilternquizleague.util.UUID
import org.chilternquizleague.maintain.service.EntityService
import angulate2.router.Router
import js.annotation.ScalaJSDefined


trait ListComponent[T]{
  
  val service:EntityService[T]
  val router:Router
  var items:js.Array[T]
  

  def addNewItem():Unit = {
    for(
      (id,item) <-Some(instance())
    )
    yield{
      service.put(id,item)
      router.navigateTo(s"/${service.name}",id)
    }
  }
  
  def onInit() = service.list.subscribe(this.items = _)
  
  def instance():(String,T)
  
  
}

