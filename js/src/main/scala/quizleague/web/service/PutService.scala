package quizleague.web.service

import firebase.Promise

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
    saveDom(item.withKey(Key(parentKey,uriRoot,item.id)))
  }

  private[service] def saveDom(i:U):Observable[Unit] = {
    val path = i.key.getOrElse(throw new RuntimeException("no key")).key
    val promise = db.doc(path).set(convertJsonToJs(enc(i.withKey(None))).asInstanceOf[js.Dictionary[js.Any]])
    log(i,s"saved $path to firestore")
    deCache(i)
    promiseToObs(promise)
  }

  private[service] def promiseToObs[X](promise:Promise[X]):Observable[X]=  {
    val obs = ReplaySubject[X]()
    promise.`then`(obs.next _, obs.error _)
    obs
  }

  def copy(item:T, parentKey:ModelKey = null):T = mapOutWithKey(mapIn(item).withKey(Key(Option(parentKey).map(_.key),uriRoot,newId())))
  def getRef(item:T):Ref[U] = Ref(key(item.key).getOrElse(null))
  def delete(item:T):Observable[Unit] = doDelete(item.key)
  def delete(id:String):Observable[Unit] = doDelete(new ModelKey(null,uriRoot,id))
  private[service] def doDelete(key:ModelKey) = {
    items -= key.id
    promiseToObs(db.doc(key.key).delete())
  }
  def instance() = add(mapOutWithKey(make()))
  def instance(parentKey:ModelKey) = add(mapOutWithKey(make(Key(parentKey.key))))
  def getId(item:T) = if (item != null ) item.id else null
  def getKey(item:T):ModelKey = Option(item).map(_.key).getOrElse(key(item.id))
  protected final def newId() = UUID.randomUUID.toString()
  private[service] def deCache(item:U) = items -= item.id
  protected final def mapInWithKey(model:T) = {
    val dom = mapIn(model)
    dom.withKey(Some(Key(model.key.key)))
  }
  
  protected def mapIn(model:T):U
  protected def make():U
  protected def make(parentKey:Key):U = {
    val u = make()
    u.withKey(Key(parentKey,uriRoot,u.id))
  }
  protected def withKey(item:U, parentKey:String):U = {
    item.withKey(new Key(Option(parentKey), uriRoot, item.id))
  }
  protected def enc(item:U):Json
  def asJSon(item:T) = enc(mapIn(item)).toString()

}