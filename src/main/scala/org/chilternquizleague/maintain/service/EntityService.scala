package org.chilternquizleague.maintain.service

import rxjs.RxPromise
import rxjs.Observable
import angulate2.http._
import scala.scalajs.js
import js.JSConverters._
import js.ArrayOps
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
  
  val uriRoot = "entities/"
  
  val http:Http
  private var items:Map[String,U] = Map()
  
  private def add(item:U) = {items = items + ((item.id, item));mapOutSparse(item)}
  def get(id:String) = items.get(id).map(mapOut(_)).getOrElse(getFromHttp(id))
  def get(ref:Ref):Observable[T] = if(ref != null) get(ref.id) else Observable.of(null).asInstanceOf[Observable[T]]
  def list():Observable[js.Array[T]] = {
    val aa = http.get(s"$uriRoot$typeName")
    val bb = aa.map((r,i) => log(r.jsonData[js.Array[String]],"list-jsonData : ").toArray)
    bb.map((a,i) => a.map(x => mapOutSparse(log(fromJson(log(x, "list-x : ")), "list-fromJson : "))).toJSArray)

    //Observable.of(items.values.map(mapOutSparse(_)).toJSArray)
  }
  def delete(item:T) = {items = items - mapIn(item).id} 
  def save(item:T) = {
    val i = log(mapIn(item), "save - mapIn : ")
    http.put(s"$uriRoot$typeName/${i.id}",log(toJson(i), "save - toJson : "))
    flush()
  }
  def flush() = items = Map()
  def instance() = add(make())
  def getId(item:T) = if (item != null ) mapIn(item).id else null
  def getRef(item:T):Ref = Ref(typeName,getId(item))
  protected final def getFromHttp(id:String) = {
    val aa = http.get(s"$uriRoot$typeName/$id")
    val bb = aa.map((r,i) => log(r.jsonData, "getFromHttp-jsonData : ").toString)
    bb.switchMap((a,i) => mapOut(log(fromJson(a), "getFromHttp-fromJson : ")))
    
  }
  protected def mapIn(model:T):U
  protected def mapOut(domain:U):Observable[T]
  protected def mapOutSparse(domain:U):T
  protected def make():U
  protected def toJson(item:U):String
  protected def fromJson(json:String):U
  
  protected final def newId() = UUID.randomUUID.toString()
  protected final def log[A](i:A, message:String=""):A = {g.console.log(message + i.asInstanceOf[js.Any]);i}

   
}



