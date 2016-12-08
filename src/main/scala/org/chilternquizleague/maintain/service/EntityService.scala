package org.chilternquizleague.maintain.service

import rxjs.RxPromise
import rxjs.Observable
import angulate2.http.Http
import scala.scalajs.js
import js.JSConverters._
import scala.scalajs.js.annotation.ScalaJSDefined

abstract class EntityService[T]{
  
  val http:Http
  val name:String
  private var items:Map[String,T] = Map()
  
  def put(id:String,item:T):T = {items = items + ((id, item));item}
  def get(id:String) = Observable.of(items(id))
  def list() = Observable.of(items.values.toJSArray)
  
}