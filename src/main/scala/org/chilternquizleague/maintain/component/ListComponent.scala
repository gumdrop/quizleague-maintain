package org.chilternquizleague.maintain.component

import angulate2.core.OnInit
import scala.scalajs.js
import js.JSConverters._
import org.chilternquizleague.util.UUID
import org.chilternquizleague.maintain.service.EntityService
import angulate2.router.Router
import js.annotation.ScalaJSDefined


@ScalaJSDefined
abstract class ListComponent[T] extends OnInit{
  
  val service:EntityService[T]
  val router:Router
  var items:js.Array[T] = _
  
  def addNew():Unit = {
    for(
      (id,item) <-Some(instance())
    )
    yield{
      service.put(id,item)
      router.navigateTo(s"/${service.name}",id)
    }
  }
  
  override def ngOnInit() = service.list.subscribe(this.items = _)
  
  def instance():(String,T)
  
  
}

