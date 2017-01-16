package org.chilternquizleague.maintain.service

import rxjs.RxPromise
import rxjs.Observable
import angulate2.http.Http
import scala.scalajs.js
import js.JSConverters._
import scala.scalajs.js.annotation.ScalaJSDefined
import org.chilternquizleague.maintain.component.IdStuff
import org.chilternquizleague.maintain.domain.Entity
import org.chilternquizleague.util.UUID
import org.chilternquizleague.maintain.domain.Ref
import org.chilternquizleague.maintain.component.ComponentNames

import js.Dynamic.{ global => g }

trait EntityService[T]{
  this:ComponentNames =>
  type U <: Entity
  
  val http:Http
  private var items:Map[String,U] = Map()
  
  private def add(item:U) = {items = items + ((item.id, item));mapOutSparse(item)}
  def get(id:String) = items.get(id).map(mapOut(_)).getOrElse(Observable.of(null).asInstanceOf[Observable[T]])
  def get(ref:Ref[U]):Observable[T] = if(ref != null) get(ref.id) else Observable.of(null).asInstanceOf[Observable[T]]
  def list():Observable[js.Array[T]] = Observable.of(items.values.map(mapOutSparse(_)).toJSArray)
  def delete(item:T) = {items = items - mapIn(item).id} 
  def save(item:T) = add(mapIn(item))
  def flush() = list()
  def instance() = add(make())
  def getId(item:T) = if (item != null ) mapIn(item).id else null
  def getRef(item:T):Ref[U] = Ref(typeName,getId(item))
  protected def mapIn(model:T):U
  protected def mapOut(domain:U):Observable[T]
  protected def mapOutSparse(domain:U):T
  protected def make():U
  
  protected final def newId() = UUID.randomUUID.toString()
  protected final def log[A](i:A):A = {g.console.log(js.JSON.stringify(i.asInstanceOf[js.Any]));i}

   
}