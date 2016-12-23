package org.chilternquizleague.maintain.service

import rxjs.RxPromise
import rxjs.Observable
import angulate2.http.Http
import scala.scalajs.js
import js.JSConverters._
import scala.scalajs.js.annotation.ScalaJSDefined
import org.chilternquizleague.maintain.component.IdStuff

trait EntityService[T]{
  
  this:IdStuff[T] =>
  
  val http:Http
  private var items:Map[String,T] = Map()
  
  def add(item:T):T = {items = items + ((getId(item), item));item}
  def get(id:String) = Observable.of(items(id))
  def list() = Observable.of(items.values.toJSArray)
  def delete(item:T) = {items = items - getId(item)} 
  def save(item:T) = {}
  def flush() = {Observable.of(items.values.toJSArray)}
   
}