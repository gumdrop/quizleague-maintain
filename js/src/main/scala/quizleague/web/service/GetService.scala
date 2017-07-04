package quizleague.web.service

import scala.scalajs.js
import scala.scalajs.js.JSConverters._

import angulate2.http.Http
import quizleague.domain.{ Entity, Ref }
import quizleague.web.names.ComponentNames
import quizleague.web.util.Logging
import rxjs.Observable
import io.circe.Error
import quizleague.web.util.rx._

trait GetService[T] extends Logging {
  this: ComponentNames =>
  type U <: Entity

  val serviceRoot: String

  lazy val uriRoot = s"$serviceRoot/$typeName"

  val http: Http
  private[service] var items: Map[String, U] = Map()
  val requestOptions = js.Dynamic.literal()

  def get(id: String)(implicit depth: Int = 1): Observable[T] = items.get(id).map(mapOut(_)).getOrElse(getFromHttp(id).switchMap((u, i) => mapOut(u)))
  def getRO(id:String):RefObservable[T] = RefObservable(id,get(id)(0))
  protected def getSparse(id: String): Observable[T] = items.get(id).map(u => Observable.of(mapOutSparse(u))).getOrElse(getFromHttp(id).map((u, i) => mapOutSparse(u)))

  def list(): Observable[js.Array[T]] = http.get(s"$uriRoot", requestOptions)
    .map((r, i) => decList(r.asInstanceOf[js.Dynamic].text().toString).merge.asInstanceOf[List[U]])
    .map((a, i) => a.map(x => mapOutSparse(x)).toJSArray)

  def flush() = items = Map()

  protected final def add(item: U) = { items = items + ((item.id, item)); mapOutSparse(item) }
  protected final def getFromHttp(id: String): Observable[U] = {

    http.get(s"$uriRoot/$id", requestOptions).
      map((r, i) => postProcess(dec(r.asInstanceOf[js.Dynamic].text().toString).merge.asInstanceOf[U]))
      .onError((x, t) => { log(s"error in GET for path $uriRoot/$id : $x : $t"); Observable.of(null).asInstanceOf[Observable[U]] })

  }
  
  protected[service] def postProcess(u:U):U = u
  
  protected def dec(json:String):Either[Error,U]
  protected def decList(json:String):Either[Error,List[U]]

  protected final def child[A <: Entity, B](ref: Ref[A], service: GetService[B])(implicit depth: Int): Observable[B] = if (ref != null) service.get(ref.id)(depth - 1) else Observable.of(null.asInstanceOf[B])
  protected final def mapOutList[A <: Entity, B](list: List[Ref[A]], service: GetService[B])(implicit depth: Int): Observable[js.Array[B]] =
    if (list.isEmpty) Observable.of(js.Array[B]()) else Observable.zip(list.map((a: Ref[A]) => child(a, service)): _*)

  private[service] def getDom(id: String) = items(id)

  final def refObs(id:String):RefObservable[T] = RefObservable(id, get(id))
  final def refObs(opt:Option[Ref[U]]):RefObservable[T] = opt.fold[RefObservable[T]](null)(ref => refObs[U,T](ref,this))
  protected final def refObs[A <: Entity, B](ref: Ref[A], service: GetService[B]):RefObservable[B] = RefObservable(ref, service.get(ref.id))
  protected final def refObsList[A <: Entity, B](refs:List[Ref[A]], service:GetService[B]):js.Array[RefObservable[B]] = refs.map(refObs(_,service)).toJSArray
  
  def ref(id:String):Ref[U] = Ref(typeName,id)
  def ref(list:js.Array[RefObservable[T]]):List[Ref[U]] = list.map(ref _).toList
  def ref(ro:RefObservable[T]):Ref[U] = if(ro == null) null else Ref(typeName,ro.id)
  
  protected final def mapOut(domain: U): Observable[T] = Observable.of(mapOutSparse(domain))
  protected def mapOutSparse(domain: U): T
  

}


