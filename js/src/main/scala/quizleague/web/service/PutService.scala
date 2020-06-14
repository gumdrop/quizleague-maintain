package quizleague.web.service

import scalajs.js
import quizleague.web.util.UUID
import quizleague.domain.{Key, Ref}
import quizleague.web.names.ComponentNames
import quizleague.web.util.rx.RefObservable
import io.circe.Json
import io.circe.scalajs._
import quizleague.web.util.Logging._
import quizleague.web.model.{Model, Key => ModelKey}
import rxscalajs.Observable
import rxscalajs.subjects.ReplaySubject

trait PutService[T <: Model] {
  this: GetService[T] with ComponentNames=>
 
  def cache(item: T) = add(item)
  
  protected def add(entity:U):T = add(mapOutWithKey(entity))

  def save(item: T):Observable[Unit] = save(mapInWithKey(item))

  def save(obs:RefObservable[T]):Unit = obs.subscribe(save(_))

  protected def save(item:U):Observable[Unit] = saveDom(item)
  protected def save(item:U, parentKey:Key = null):Observable[Unit] = {
    item.key = Some(Key(parentKey,uriRoot,item.id))
    saveDom(item)
  }

  private[service] def saveDom(i:U):Observable[Unit] = {
    val path = i.key.getOrElse(throw new RuntimeException("no key")).key
    val promise = db.doc(path).set(convertJsonToJs(enc(i.withKey(None))).asInstanceOf[js.Dictionary[js.Any]])
    val obs = ReplaySubject[Unit]()
    promise.`then`({obs.next(_)}, {obs.error(_)})
    log(i,s"saved $path to firestore")
    deCache(i)
    obs
  }

  def copy(item:T, parentKey:ModelKey = null):T = mapOutWithKey(mapIn(item).withKey(Key(Option(parentKey).map(_.key),uriRoot,newId())))
  def getRef(item:T):Ref[U] = Ref(typeName,getId(item))
  def delete(item:T):Unit = doDelete(item.id)
  def delete(id:String):Unit = doDelete(id)
  private[service] def doDelete(id:String):Unit = {items -= id; db.doc(s"$uriRoot/$id").delete()}
  def instance() = add(mapOutWithKey(make()))
  def instance(parentKey:ModelKey) = add(mapOutWithKey(make(Key(parentKey.key))))
  def getId(item:T) = if (item != null ) item.id else null
  protected final def newId() = UUID.randomUUID.toString()
  private[service] def deCache(item:U) = items -= item.id
  protected final def mapInWithKey(model:T) = {
    val dom = mapIn(model)
    dom.key = Some(Key(model.key.key))
    dom
  }
  
  protected def mapIn(model:T):U
  protected def make():U
  protected def make(parentKey:Key):U = {
    val u = make()
    u.withKey(Key(parentKey,uriRoot,u.id))
  }
  protected def enc(item:U):Json
  def asJSon(item:T) = enc(mapIn(item)).toString()

}