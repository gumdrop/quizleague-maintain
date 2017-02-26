package quizleague.web.service

import rxjs.RxPromise
import rxjs.Observable
import angulate2.http._
import scala.scalajs.js
import js.JSConverters._
import js.ArrayOps
import scala.scalajs.js.annotation.ScalaJSDefined

import quizleague.domain.Entity
import quizleague.web.util._
import quizleague.domain.Ref
import quizleague.web.maintain.component.ComponentNames

trait GetService[T] extends Logging{
  this:ComponentNames =>
  type U <: Entity
  
  val serviceRoot:String
  
  lazy val uriRoot = s"$serviceRoot/$typeName"
  
  val http:Http
  private[service] var items:Map[String,U] = Map()
  val requestOptions = js.Dynamic.literal(responseType = "Text")
  
  def get(id:String):Observable[T] = items.get(id).map(mapOut(_)).getOrElse(getFromHttp(id).switchMap((u,i) => mapOut(u)))
  def getSparse(id:String):Observable[T] = items.get(id).map(u => Observable.of(mapOutSparse(u))).getOrElse(getFromHttp(id).map((u,i) => mapOutSparse(u)))
  def get(ref:Ref[U]):Observable[T] = if(ref != null && ref.id != null) get(ref.id) else Observable.of(null).asInstanceOf[Observable[T]]
  def list():Observable[js.Array[T]] = http.get(s"$uriRoot",requestOptions)
    .map((r,i) => r.jsonData[js.Array[js.Dynamic]].toArray)
    .map((a,i) => a.map(x => add(unwrap(x))).toJSArray)
  def flush() = items = Map()
  
  protected final def add(item:U) = {items = items + ((item.id, item));mapOutSparse(item)}
  protected final def getFromHttp(id:String):Observable[U] = {
    
    http.get(s"$uriRoot/$id",requestOptions).
      map((r,i) => r.jsonData[js.Dynamic]).
      map((a,i) => {
        val u = unwrap(a)
        items = items + ((u.id, u))
        u
      }
    ).onError((x,t) => Observable.of(null).asInstanceOf[Observable[U]])

  }
    

  protected final def mapOutList[A <: Entity,B](list:List[Ref[A]], service:GetService[B], sparse:Boolean=true):Observable[js.Array[B]] = 
     if(list.isEmpty) Observable.of(js.Array[B]()) else Observable.zip(list.map((a:Ref[A]) => if(sparse) service.getSparse(a.id) else service.get(a.id)):_*)

  private[service] def getDom(id:String) = items(id)

  private def unwrap(obj:js.Dynamic) = fromJson(obj.json.toString)

  private def fromJson(jsonString:String):U = if(jsonString == null) null.asInstanceOf[U] else deser(jsonString)
     

  protected def mapOut(domain:U):Observable[T]
  protected def mapOutSparse(domain:U):T
  protected def deser(json:String):U

}


