package org.chilternquizleague.maintain.service

import rxjs.RxPromise
import rxjs.Observable
import angulate2.http._
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
  def get(id:String) = items.get(id).map(mapOut(_)).getOrElse(mapOut(fromJson("" + g.localStorage.getItem(id))))
  def get(ref:Ref):Observable[T] = if(ref != null) get(ref.id) else Observable.of(null).asInstanceOf[Observable[T]]
  def list():Observable[js.Array[T]] = http.get(s"entities/$typeName").map((r,i) => r.jsonData[js.Array[String]]).map((a:js.Array[String]) => a.map(s:String => mapOutSparse(s)))//Observable.of(items.values.map(mapOutSparse(_)).toJSArray)
  def delete(item:T) = {items = items - mapIn(item).id} 
  def save(item:T) = {
    val i = mapIn(item)
    add(i); 
    http.put(s"/entities/$typeName/${i.id}",toJson(i))
  }
  def flush() = list()
  def instance() = add(make())
  def getId(item:T) = if (item != null ) mapIn(item).id else null
  def getRef(item:T):Ref = Ref(typeName,getId(item))
  protected def mapIn(model:T):U
  protected def mapOut(domain:U):Observable[T]
  protected def mapOutSparse(domain:U):T
  protected def make():U
  protected def toJson(item:U):String
  protected def fromJson(json:String):U
  
  protected final def newId() = UUID.randomUUID.toString()
  protected final def log[A](i:A):A = {g.console.log(js.JSON.stringify(i.asInstanceOf[js.Any]));i}

   
}



