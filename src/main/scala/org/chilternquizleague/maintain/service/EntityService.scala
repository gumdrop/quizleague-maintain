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
import org.chilternquizleague.util._
import org.chilternquizleague.maintain.domain.Ref
import org.chilternquizleague.maintain.component.ComponentNames

trait EntityService[T] extends EntitySave[T] with Logging{
  this:ComponentNames =>
  type U <: Entity
  
  lazy val uriRoot = s"entities/$typeName"
  
  val http:Http
  private var items:Map[String,U] = Map()
  val requestOptions = js.Dynamic.literal(responseType = "Text")
  
  def get(id:String) = items.get(id).map(mapOut(_)).getOrElse(getFromHttp(id))
  def get(ref:Ref[U]):Observable[T] = if(ref != null && ref.id != null) get(ref.id) else Observable.of(null).asInstanceOf[Observable[T]]
  def list():Observable[js.Array[T]] = http.get(s"$uriRoot",requestOptions)
    .map((r,i) => r.jsonData[js.Array[js.Dynamic]].toArray)
    .map((a,i) => a.map(x => add(unwrap(x))).toJSArray)

  def delete(item:T) = {items = items - mapIn(item).id} 
  
  protected final def add(item:U) = {items = items + ((item.id, item));mapOutSparse(item)}

  
  def flush() = items = Map()
  def instance() = add(make())
  def getId(item:T) = if (item != null ) mapIn(item).id else null
  def getRef(item:T):Ref[U] = Ref(typeName,getId(item))
  
  protected final def getFromHttp(id:String) = 
    http.get(s"$uriRoot/$id",requestOptions).
      map((r,i) => r.jsonData[js.Dynamic]).
      switchMap((a,i) => {
        val u = unwrap(a)
        items = items + ((u.id, u))
        mapOut(u)
      }
    )
  
  protected final def newId() = UUID.randomUUID.toString()
  protected final def mapOutList[A <: Entity,B](list:List[Ref[A]], service:EntityService[B]):Observable[js.Array[B]] = 
     if(list.isEmpty) Observable.of(js.Array[B]()) else Observable.zip(list.map((a:Ref[A]) => service.get(a.id)):_*)

  private[service] def wrap(item:U) = js.Dynamic.literal(id = item.id, json = toJson(item))
  private[service] def getDom(id:String) = items(id)
  private[service] def deCache(item:U) = items = items - item.id
  private def unwrap(obj:js.Dynamic) = fromJson(obj.json.toString)
  
  private def toJson(item:U) = if(item != null) ser(item) else null

  private def fromJson(jsonString:String):U = if(jsonString == null) null.asInstanceOf[U] else deser(jsonString)
  
    
  protected def mapIn(model:T):U
  protected def mapOut(domain:U):Observable[T]
  protected def mapOutSparse(domain:U):T
  protected def make():U
  protected def ser(item:U):String
  protected def deser(json:String):U

}


