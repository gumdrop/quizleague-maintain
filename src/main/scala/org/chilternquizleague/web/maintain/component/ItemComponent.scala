package org.chilternquizleague.web.maintain.component

import org.chilternquizleague.web.service.EntityService
import angulate2.router.ActivatedRoute
import angulate2.common.Location
import scala.scalajs.js
import js.annotation.ScalaJSDefined
import angulate2.core.OnInit
import js.annotation.JSExport
import org.chilternquizleague.web.service.PutService
import org.chilternquizleague.web.service.GetService

trait ItemComponent[T] extends OnInit{
  val service:GetService[T] with PutService[T]
  val route:ActivatedRoute
  val location:Location
  

  @JSExport
  var item:T = _
  
  @JSExport
  def save():Unit = {service.save(item);location.back()}
  
  @JSExport
  def cancel():Unit = {service.flush();location.back()}

  @JSExport
  def ngOnInit() = init()
  
  def init(): Unit = route.params
    .switchMap( (params,i) => service.get(params("id")) )
    .subscribe(this.item = _)
}

