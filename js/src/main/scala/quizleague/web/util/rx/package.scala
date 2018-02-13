package quizleague.web.util

package object rx {
  import scala.language.implicitConversions
  import scalajs.js
  import rxscalajs.Observable
  import rxscalajs.Observable._
  import scalajs.js.JSConverters._
  import quizleague.web.util.Logging._

  implicit def refObsToObs[T](refObs: RefObservable[T]) = refObs.obs
  

  def combineLatest[A](list:Observable[A]*) = if(list.isEmpty) just(js.Array[A]()) else Observable.combineLatest(list.toSeq).map(_.toJSArray)
  
  def zip[A](list: js.Array[RefObservable[A]]):Observable[js.Array[A]] = if(list.isEmpty) just(js.Array[A]()) else Observable.combineLatest(list.map(_.obs).toSeq).map(_.toJSArray)
//  implicit def zipRO[A](list: js.Array[RefObservable[A]]) = RefObservable("dummy",zip(list))
}

//  import scala.language.implicitConversions
//  import scala.scalajs.js
//  import rxjs.Observable
//  import scalajs.js.JSConverters._
//  import quizleague.web.util.Logging._
//
//  implicit def refObsToObs[T](refObs: RefObservable[T]) = refObs.obs
//
//  implicit def zip[A](list: js.Array[RefObservable[A]]) = if(list.isEmpty) Observable.of(js.Array[A]()) else Observable.zip(list.map(_.obs): _*)
//  implicit def zipRO[A](list: js.Array[RefObservable[A]]) = RefObservable("dummy",zip(list))
//
//  def filterAndSort1[T, U](
//    list: js.Array[T],
//    extract: (T => RefObservable[U]),
//    filter: ((T, U) => Boolean),
//    sort: (((T, U), (T, U)) => Int)): Observable[js.Array[T]] = {
//
//    val interim = Observable.zip(list.map(t => extract(t).obs.map((u, i) => (t, u))): _*)
//      .map((tu, i) => tu
//        .filter(x => filter(x._1, x._2))
//        .sort((x: (T, U), y: (T, U)) => sort(x, y)).map(_._1))
//
//    interim
//
//  }
//
//  def filterAndSort[T, U](
//    list: js.Array[RefObservable[T]],
//    extract: (T => RefObservable[U]),
//    filter: ((T, U) => Boolean),
//    sort: (((T, U), (T, U)) => Int)): Observable[js.Array[T]] = {
//
//    val interim = zip(list).switchMap((l, i) => Observable.zip(l.map(t => extract(t).obs.map((u, i) => (t, u))): _*))
//      .map((tu, i) => tu
//        .filter(x => filter(x._1, x._2))
//        .sort((x: (T, U), y: (T, U)) => sort(x, y)).map(_._1))
//
//    interim
//
//  }
//
//  def sort1[T,U](list: js.Array[T], extract:T => RefObservable[U], sort:((T,U),(T,U)) => Int, take:Int = Integer.MAX_VALUE) = 
//    if(list.isEmpty) Observable.of(list) else Observable.zip(list.map(t => extract(t).obs.map((u,i) => (t,u))):_*).map((x,i) => x.sort((a:(T,U),b:(T,U)) => sort(a,b)).map(_._1).take(take))
//    
//  def sort[T](list: js.Array[RefObservable[T]], sort:((T,T) => Int), take:Int = Integer.MAX_VALUE) = zip(list).map((l,i) => l.sort((x:T,y:T) => sort(x,y)).take(take))
//  
//  def sort2[T,U](list: js.Array[RefObservable[T]],extract:T => RefObservable[U], sortfn:((T,U),(T,U)) => Int, take:Int = Integer.MAX_VALUE) = 
//    zip(list).switchMap((l,i) => sort1(l,extract,sortfn,take))
//  
//  
//  def filter[T](list: js.Array[RefObservable[T]], filter:(T => Boolean)) = zip(list).map((l,i) => l.filter(filter))
//  
//  
//  def extract1[T,U,R](t:T, x1:T => RefObservable[U])(extract:(T,U) => R) = { 
//    val xx1 = x1(t).obs
//    
//    xx1.map((u,i) => (t,u)).map((x,i) => {x match {case (t,u) => extract(t,u)}})
//  }
//  
//  def extract1[T,U,R](list:js.Array[RefObservable[T]], x1:T => RefObservable[U])(map:(T,U) => R):Observable[js.Array[R]] = { 
//    
//    if(list.isEmpty) return Observable.of(js.Array())
//    val tlist = zip(list)
//    val xx1 = tlist.switchMap((l,i) => Observable.zip(l.map(t => x1(t).obs.map((u,i) => (t,u))):_*))
//    
//    xx1.map((xl,i) => xl.map(x => map(x._1,x._2)))
//  }
//  
//  def extract2[T,U,V,R](t:T, x1:T => RefObservable[U], x2:U => RefObservable[V])(map:(T,U,V) => R) = { 
//    val xx1 = x1(t).obs
//    val xx2 = xx1.switchMap((a,i) => x2(a).obs.map((b,i) => b))
//    
//    Observable.zip(
//       xx1,
//       xx2,
//       (u:U,v:V) => ((t,u,v))).map((x,i) => {x match {case (t,u,v) => map(t,u,v)}})
//  }
   
//}