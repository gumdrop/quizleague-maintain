package quizleague.web.service

import scalajs.js
import quizleague.web.util.UUID
import quizleague.domain.Ref
import quizleague.web.names.ComponentNames
import quizleague.web.util.rx.RefObservable
import io.circe.Json
import io.circe.scalajs._
import quizleague.web.util.Logging._
import rxscalajs.Observable
import quizleague.web.model.Model

trait PutService[T <: Model] {
  this: GetService[T] with ComponentNames=>
 
  def cache(item: T) = add(item)
  
  protected def add(entity:U):T = add(mapOutSparse(entity))
  
  def save(item: T):Unit = save(mapIn(item))
  
  def save(obs:RefObservable[T]):Unit = obs.subscribe(save(_))
  
  protected def save(item:U):Unit = saveDom(item)
  
  private[service] def saveDom(i:U) = {
    db.doc(s"$uriRoot/${i.id}").set(convertJsonToJs(enc(i)).asInstanceOf[js.Dictionary[js.Any]])
    log(i,s"saved $uriRoot/${i.id} to firestore")
    deCache(i)
  }
  
  
  def getRef(item:T):Ref[U] = Ref(typeName,getId(item))
  def delete(item:T) = {items -= mapIn(item).id} 
  def instance() = add(mapOutSparse(make()))
  def getId(item:T) = if (item != null ) item.id else null
  protected final def newId() = UUID.randomUUID.toString()
  private[service] def deCache(item:U) = items -= item.id

  
  protected def mapIn(model:T):U
  protected def make():U
  protected def enc(item:U):Json


}