package quizleague.web.service

import scala.scalajs.js
import scala.scalajs.js.JSConverters._

import quizleague.domain.{ Entity, Ref }
import quizleague.web.names.ComponentNames
import quizleague.web.util.Logging._
import io.circe._, io.circe.parser._, io.circe.syntax._, io.circe.scalajs.convertJsToJson
import scala.collection.mutable._
import quizleague.web.util.rx._
import quizleague.web.store.Firestore
import rxscalajs._, rxscalajs.subjects._
import quizleague.web.util.Logging._
import firebase.firebase.firestore._
import quizleague.web.model.Model

trait GetService[T <: Model] {
  this: ComponentNames =>
  type U <: Entity

  lazy val uriRoot = typeName

  val db = Firestore.db
  private[service] val items: Map[String, T] = Map()
  private val observables = Map[String, Observable[U]]()
  private val refObsCache = Map[String, RefObservable[T]]()
  private var listObservable: Option[Observable[js.Array[U]]] = None

  def get(id: String): Observable[T] = items.get(id).fold(getFromStorage(id).map(mapOutSparse _).map(postProcess _))(Observable.just(_))
  def getRO(id: String): RefObservable[T] =  getRefObs(id)

  def list(): Observable[js.Array[T]] = listFromStorage.map(c => c.map(u => mapOutSparse(u)))

  def flush() = items.clear()

  protected def filterList(u:U) = true
  
  protected final def listFromStorage(): Observable[js.Array[U]] = {

    val obs = listObservable.getOrElse({

      val subject = ReplaySubject[QuerySnapshot]()

      db.collection(uriRoot).onSnapshot(subject.inner)

      subject.map(q => q.docs.map(d => dec(d.data()).fold(e => {throw e}, u => u))).map(_.filter(filterList _))
    })

    listObservable = Option(obs)
    obs
  }

  protected final def add(item: T) = { items += ((item.id, item)); item }
  protected final def getFromStorage(id: String): Observable[U] = {

    observables.getOrElseUpdate(id, {
      val subject = ReplaySubject[DocumentSnapshot]()

      db.doc(s"$uriRoot/$id").onSnapshot(subject.inner)

      subject.map(a => dec(a.data()).fold(e => {throw e}, u => u))
    })

  }

  protected[service] def postProcess(t: T): T = t

  protected def dec(json: js.Any): Either[Error, U]

  private[service] def getDom(id: String) = items(id)

  protected def decodeJson[T](obj: js.Any)(implicit dec: Decoder[T]) = convertJsToJson(obj).fold(t => null, dec.decodeJson(_))

  protected[service] def getRefObs(id:String):RefObservable[T] = refObsCache.getOrElseUpdate(id, RefObservable(id, () => get(id)))
  final def refObs(id: String): RefObservable[T] = getRefObs(id)
  final def refObs(opt: Option[Ref[U]]): RefObservable[T] = opt.fold[RefObservable[T]](null)(ref => refObs(ref.id))
  protected final def refObs[A <: Entity, B <: Model](ref: Ref[A], service: GetService[B]): RefObservable[B] = if(ref == null) null else service.getRefObs(ref.id)
  protected final def refObsList[A <: Entity, B <: Model](refs: List[Ref[A]], service: GetService[B]): js.Array[RefObservable[B]] = refs.map(refObs(_, service)).toJSArray

  def ref(id: String): Ref[U] = Ref(typeName, id)
  def ref(list: js.Array[RefObservable[T]]): List[Ref[U]] = list.map(ref _).toList
  def ref(ro: RefObservable[T]): Ref[U] = if (ro == null) null else Ref(typeName, ro.id)
  def refOption(ro: RefObservable[T]): Option[Ref[U]] = if (ro == null) None else Some(Ref(typeName, ro.id))
  def ref(dom: U): Ref[U] = ref(dom.id)

  protected final def mapOut(domain: U): Observable[T] = Observable.of(mapOutSparse(domain))
  protected def mapOutSparse(domain: U): T

}


