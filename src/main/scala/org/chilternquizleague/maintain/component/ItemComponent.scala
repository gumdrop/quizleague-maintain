package org.chilternquizleague.maintain.component

import org.chilternquizleague.maintain.service.EntityService
import angulate2.router.ActivatedRoute
import angulate2.common.Location
import scala.scalajs.js
import js.annotation.ScalaJSDefined
import angulate2.core.OnInit
import scala.scalajs.js.annotation.JSExport
import scala.scalajs.js.annotation.JSExport


trait ItemComponent[T] extends OnInit{
  val service:EntityService[T]
  val route:ActivatedRoute
  val location:Location
  

  @JSExport
  var item:T = _
  
  @JSExport
  def save():Unit = location.back()

  @JSExport
  def ngOnInit() = init()
  
  def init(): Unit = route.params
    .switchMap( (params,i) => service.get(params("id")) )
    .subscribe(this.item = _)
}

