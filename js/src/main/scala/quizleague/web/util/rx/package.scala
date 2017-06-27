package quizleague.web.util

package object rx {

  import scala.language.implicitConversions
  import scala.scalajs.js
  import rxjs.Observable
  import scalajs.js.JSConverters._

  implicit def refObsToObs[T](refObs: RefObservable[T]) = refObs.obs

  implicit def zip[A](list: js.Array[RefObservable[A]]) = Observable.zip(list.map(_.obs): _*)

  def filterAndSort1[T, U](
    list: js.Array[T],
    extract: (T => RefObservable[U]),
    filter: ((T, U) => Boolean),
    sort: (((T, U), (T, U)) => Int)): Observable[js.Array[T]] = {

    val interim = Observable.zip(list.map(t => extract(t).obs.map((u, i) => (t, u))): _*)
      .map((tu, i) => tu
        .filter(x => filter(x._1, x._2))
        .sort((x: (T, U), y: (T, U)) => sort(x, y)).map(_._1))

    interim

  }

  def filterAndSort[T, U](
    list: js.Array[RefObservable[T]],
    extract: (T => RefObservable[U]),
    filter: ((T, U) => Boolean),
    sort: (((T, U), (T, U)) => Int)): Observable[js.Array[T]] = {

    val interim = zip(list).switchMap((l, i) => Observable.zip(l.map(t => extract(t).obs.map((u, i) => (t, u))): _*))
      .map((tu, i) => tu
        .filter(x => filter(x._1, x._2))
        .sort((x: (T, U), y: (T, U)) => sort(x, y)).map(_._1))

    interim

  }

  def sort[T](list: js.Array[RefObservable[T]], sort:((T,T) => Int)) = zip(list).map((l,i) => l.sort(sort))
  def filter[T](list: js.Array[RefObservable[T]], filter:(T => Boolean)) = zip(list).map((l,i) => l.filter(filter))
  
  def action2[T,U,V](t:T, x1:T => RefObservable[U], x2:U => RefObservable[V], action:((T,U,V) => Unit)) = { 
    val xx1 = x1(t).obs
    val xx2 = xx1.switchMap((a,i) => x2(a).obs.map((b,i) => b))
    
    Observable.zip(
       xx1,
       xx2,
       (u:U,v:V) => ((t,u,v))).subscribe({case (t,u,v) => action(t,u,v)})
  }
}