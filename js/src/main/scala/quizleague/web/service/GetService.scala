package quizleague.web.service

import scala.scalajs.js
import scala.scalajs.js.JSConverters._

import angulate2.http.Http
import quizleague.domain.{ Entity, Ref }
import quizleague.web.names.ComponentNames
import quizleague.web.util.Logging
import rxjs.Observable
import quizleague.web.util.rx.RefObservable

trait GetService[T] extends Logging {
  this: ComponentNames =>
  type U <: Entity

  val serviceRoot: String

  lazy val uriRoot = s"$serviceRoot/$typeName"

  val http: Http
  private[service] var items: Map[String, U] = Map()
  val requestOptions = js.Dynamic.literal(responseType = "Text")

  def get(id: String)(implicit depth: Int = 1): Observable[T] = if (depth <= 0) getSparse(id) else items.get(id).map(mapOut(_)).getOrElse(getFromHttp(id).switchMap((u, i) => mapOut(u)))
  protected def getSparse(id: String): Observable[T] = items.get(id).map(u => Observable.of(mapOutSparse(u))).getOrElse(getFromHttp(id).map((u, i) => mapOutSparse(u)))

  def list(): Observable[js.Array[T]] = http.get(s"$uriRoot", requestOptions)
    .map((r, i) => r.jsonData[js.Array[js.Dynamic]].toArray)
    .map((a, i) => a.map(x => add(unwrap(x))).toJSArray)

  def flush() = items = Map()

  protected final def add(item: U) = { items = items + ((item.id, item)); mapOutSparse(item) }
  protected final def getFromHttp(id: String): Observable[U] = {

    http.get(s"$uriRoot/$id", requestOptions).
      map((r, i) => r.jsonData[js.Dynamic]).
      map((a, i) => {
        val u = unwrap(a)
        items = items + ((u.id, u))
        u
      }).onError((x, t) => { log(s"error in GET for path $uriRoot/$id : $x : $t"); Observable.of(null).asInstanceOf[Observable[U]] })

  }

  protected final def child[A <: Entity, B](ref: Ref[A], service: GetService[B])(implicit depth: Int): Observable[B] = if (ref != null) service.get(ref.id)(depth - 1) else Observable.of(null.asInstanceOf[B])
  protected final def mapOutList[A <: Entity, B](list: List[Ref[A]], service: GetService[B])(implicit depth: Int): Observable[js.Array[B]] =
    if (list.isEmpty) Observable.of(js.Array[B]()) else Observable.zip(list.map((a: Ref[A]) => child(a, service)): _*)

  private[service] def getDom(id: String) = items(id)

  private def unwrap(obj: js.Dynamic) = fromJson(obj.json.toString)

  private def fromJson(jsonString: String): U = if (jsonString == null) null.asInstanceOf[U] else deser(jsonString)

  protected final def refObs[A <: Entity, B](ref: Ref[A], service: GetService[B]) = RefObservable(ref, service.get(ref.id))
  
  
  protected def mapOut(domain: U)(implicit depth: Int): Observable[T]
  protected def mapOutSparse(domain: U): T
  protected def deser(json: String): U

}


