package quizleague.web.service

import scala.collection.mutable._
import scala.scalajs.js
import scala.scalajs.js.JSConverters._

import firebase.firestore._
import io.circe._
import io.circe.scalajs.convertJsToJson
import quizleague.domain.{ Entity, Ref, Key }
import quizleague.web.model.Model
import quizleague.web.model.{Key => ModKey}
import quizleague.web.names.ComponentNames
import quizleague.web.store.Firestore
import quizleague.web.util.rx.RefObservable
import rxscalajs._
import rxscalajs.subjects._
import quizleague.web.util.Logging._

trait GetService[T <: Model] {
  this: ComponentNames =>
  type U <: Entity

  lazy val uriRoot = typeName

  protected val db = Firestore.db
  private[service] val items: Map[String, T] = Map()
  private val observables = Map[String, Observable[U]]()
  private val refObsCache = Map[String, RefObservable[T]]()
  private var listObservables: Map[String, Observable[js.Array[U]]] = Map()

  def get(id: String): Observable[T] = get(key(id))
  def get(key: ModKey): Observable[T] = items.get(key.id).fold(getFromStorage(key).map(mapOutWithKey _).map(postProcess _))(Observable.just(_))
  def getRO(id: String): RefObservable[T] =  getRefObs(id)
  def getRO(key:Key) = getRefObs(key)
  def key(id:String):ModKey = key(null,id)
  def key(parentKey:String, id:String):ModKey = new ModKey(parentKey,uriRoot,id)
  def key(key:Option[Key]):ModKey = key.map(k => ModKey(k.key)).getOrElse(null)
  def key(key:Key):ModKey = ModKey(key.key)
  def key(key:ModKey):Option[Key] = Option(key).map(k => Key(k.key))

  def list(parentKey:Option[Key]): Observable[js.Array[T]] = list(parentKey.map(k => ModKey(k.key)).getOrElse(null))
  def list(parentKey:ModKey=null): Observable[js.Array[T]] = listFromStorage(parentKey).map(c => c.map(u => mapOutWithKey(u)))
  def groupQuery():Query = db.collectionGroup(uriRoot)
  protected def query(query:Query):Observable[js.Array[T]] = listFromQuery(query).map(_.map(mapOutWithKey _))

  def flush() = items.clear()

  protected def filterList(u:U) = true
  
  protected final def listFromStorage(parentKey:ModKey = null): Observable[js.Array[U]] = {
    
    listObservables.getOrElseUpdate(s"$parentKey",{listFromQuery(db.collection(s"${if(parentKey == null)""else s"${parentKey.key}/"}$uriRoot"))})
  }
  
  protected final def listFromQuery(query:Query): Observable[js.Array[U]] = {

      val subject = ReplaySubject[QuerySnapshot]()

      query.onSnapshot(subject.inner)

      subject.map(q => q.docs.map(d => dec(d.data()).fold(e => {throw e}, u => u.withKey(Key(d.ref.path))))).map(_.filter(filterList _))
   
  }


  protected final def add(item: T) = { items += ((item.id, item)); item }
  protected final def getFromStorage(key: ModKey): Observable[U] = {

    observables.getOrElseUpdate(key.id, {
      val subject = ReplaySubject[DocumentSnapshot]()

      db.doc(key.key).onSnapshot(subject.inner)

      subject
        .map(a => if(a.exists) dec(a.data())
          .fold(e => {throw e}, u => u
            .withKey(Key(a.ref.path))
          )
        else {throw new Exception(s"db load failed : $key not found")})
    })

  }

  protected[service] def postProcess(t: T): T = t

  protected def dec(json: js.Any): Either[Error, U]

  private[service] def getDom(id: String) = items(id)

  protected def decodeJson[X](obj: js.Any)(implicit dec: Decoder[X]) = convertJsToJson(obj).fold(t => null, dec.decodeJson(_))

  protected[service] def getRefObs(id:String):RefObservable[T] = refObsCache.getOrElseUpdate(key(id).toString, RefObservable(key(id), () => get(id)))
  protected[service] def getRefObs(domKey:Key):RefObservable[T] = refObsCache.getOrElseUpdate(domKey.toString, RefObservable(key(domKey), () => get(key(domKey))))

  final def refObs(id: String): RefObservable[T] = getRefObs(id)
  final def refObs(modKey:ModKey): RefObservable[T] = getRefObs(key(modKey).getOrElse(null))
  final def refObs(opt: Option[Ref[U]]): RefObservable[T] = opt.fold[RefObservable[T]](null)(ref => getRefObs(ref.getKey()))
  protected final def refObs[A <: Entity, B <: Model](ref: Ref[A], service: GetService[B]): RefObservable[B] = if(ref == null) null else service.getRefObs(ref.getKey())

  def ref(id: String): Ref[U] = Ref(typeName, id)
  def ref(ro: RefObservable[T]): Ref[U] = if (ro == null) null else Ref(typeName, ro.id, key(ro.key))
  def refOption(ro: RefObservable[T]): Option[Ref[U]] = if (ro == null) None else Some(Ref(typeName, ro.id, key(ro.key)))
  def ref(dom: U): Ref[U] = Ref(typeName, dom.id, dom.key)

  protected final def mapOut(domain: U): Observable[T] = Observable.of(mapOutWithKey(domain))
  protected def mapOutSparse(domain: U): T
  protected final def mapOutWithKey(domain:U) = {
    val t = mapOutSparse(domain)
    t.key = ModKey(domain.key.getOrElse(throw new RuntimeException("no domain key")))
    t
  }

}


