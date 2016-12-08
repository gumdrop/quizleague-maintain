package org.chilternquizleague.maintain.component

import org.chilternquizleague.maintain.service.EntityService
import angulate2.router.ActivatedRoute
import angulate2.common.Location
import scala.scalajs.js
import js.annotation.ScalaJSDefined
import angulate2.core.OnInit


abstract class ItemComponent[T]{
  val service:EntityService[T]
  val route:ActivatedRoute
  val location:Location
  

  var item:T
  def save():Unit
  
  protected def saveIt(): Unit = location.back()
  
  def onInit(): Unit = route.params
    .switchMap( (params,i) => service.get(params("id")) )
    .subscribe(this.item = _)
}

