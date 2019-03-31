package quizleague.web.service

import scalajs.js
import quizleague.web.util.UUID
import quizleague.domain.Ref
import quizleague.web.names.ComponentNames
import quizleague.web.util.rx.RefObservable
import io.circe.Json
import io.circe.scalajs._
import quizleague.web.util.Logging._
import quizleague.web.model.Model
import rxscalajs.Observable
import rxscalajs.subjects.ReplaySubject

trait PutService[T <: Model] {
  this: GetService[T] with ComponentNames=>
 
  def cache(item: T) = add(item)
  
  protected def add(entity:U):T = add(mapOutSparse(entity))
  
  def save(item: T):Observable[Unit] = save(mapIn(item))

  def saveAsChild(item:T, parentKey:String) = save(mapIn(item), parentKey)

  def save(obs:RefObservable[T]):Unit = obs.subscribe(save(_))

  protected def save(item:U):Observable[Unit] = saveDom(item)
  protected def save(item:U, parentKey:String = ""):Observable[Unit] = saveDom(item, parentKey)

  private[service] def saveDom(i:U,parentKey:String =""):Observable[Unit] = {
    val path = s"${if(parentKey.isEmpty)""else s"$parentKey/"}$uriRoot/${i.id}"
    val promise = db.doc(path).set(convertJsonToJs(enc(i)).asInstanceOf[js.Dictionary[js.Any]])
    val obs = ReplaySubject[Unit]()
    promise.`then`({obs.next(_)}, {obs.error(_)})
    log(i,s"saved $path to firestore")
    deCache(i)
    obs
  }
  
  
  def getRef(item:T):Ref[U] = Ref(typeName,getId(item))
  def delete(item:T):Unit = doDelete(item.id)
  def delete(id:String):Unit = doDelete(id)
  private[service] def doDelete(id:String):Unit = {items -= id; db.doc(s"$uriRoot/$id").delete()}
  def instance() = add(mapOutSparse(make()))
  def getId(item:T) = if (item != null ) item.id else null
  protected final def newId() = UUID.randomUUID.toString()
  private[service] def deCache(item:U) = items -= item.id

  
  protected def mapIn(model:T):U
  protected def make():U
  protected def enc(item:U):Json


}