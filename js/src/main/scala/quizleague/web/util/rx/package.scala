package quizleague.web.util

package object rx {
  
  import scala.language.implicitConversions
  import scala.scalajs.js
  import rxjs.Observable
  import scalajs.js.JSConverters._
  
  implicit def refObsToObs[T](refObs:RefObservable[T]) = refObs.obs
  
  implicit def zip[A](list:js.Array[RefObservable[A]]) = Observable.zip(list.map(_.obs):_*)
  
  def filterAndSort1[T,U](
      list:js.Array[T], 
      extract:(T => RefObservable[U]), 
      filter:((T,U) => Boolean), 
      sort:(((T,U), (T,U)) => Int)):Observable[js.Array[T]] = {
     
    val interim = Observable.zip(list.map(t => extract(t).obs.map((u,i) => (t,u))):_*)
    .map((tu,i) => tu
        .filter(x => filter(x._1, x._2))
        .sort((x:(T,U),y:(T,U)) => sort(x, y)).map(_._1))
    
     interim  

  }
  
    def filterAndSort[T,U](
      list:js.Array[RefObservable[T]], 
      extract:(T => RefObservable[U]), 
      filter:((T,U) => Boolean), 
      sort:(((T,U), (T,U)) => Int)):Observable[js.Array[T]] = {
      
    val interim = zip(list).switchMap((l,i) => Observable.zip(l.map(t => extract(t).obs.map((u,i) => (t,u))):_*))
    .map((tu,i) => tu
        .filter(x => filter(x._1, x._2))
        .sort((x:(T,U),y:(T,U)) => sort(x, y)).map(_._1))
    
     interim

  }
}