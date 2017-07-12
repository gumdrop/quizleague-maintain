package quizleague.web.service

import scalajs.js
import quizleague.web.util.UUID
import quizleague.domain.Ref
import quizleague.web.names.ComponentNames
import rxjs.Observable
import quizleague.web.util.rx.RefObservable
import io.circe.Json
import quizleague.web.util.Logging._

trait PutService[T] {
  this: GetService[T] with ComponentNames=>

  def cache(item: T) = add(mapIn(item))
  
  def save(item: T):Unit = save(mapIn(item))
  
  def save(obs:RefObservable[T]):Unit = obs.subscribe(save(_))
  
  protected def save(item:U):Unit = saveDom(item)
  
  private[service] def saveDom(i:U) = {
    http.put(s"$uriRoot/${i.id}", enc(i).noSpaces, requestOptions).subscribe(x=>x)
    log(i,s"saved $uriRoot/${i.id} to http")
    deCache(i)
  }
  
  
  def getRef(item:T):Ref[U] = Ref(typeName,getId(item))
  def delete(item:T) = {items -= mapIn(item).id} 
  def instance() = add(make())
  def getId(item:T) = if (item != null ) mapIn(item).id else null
  protected final def newId() = UUID.randomUUID.toString()
  private[service] def deCache(item:U) = items -= item.id

  
  protected def mapIn(model:T):U
  protected def make():U
  protected def enc(item:U):Json


}