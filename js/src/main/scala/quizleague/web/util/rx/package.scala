package quizleague.web.util

package object rx {
  
  import scala.language.implicitConversions
  import scala.scalajs.js
  import rxjs.Observable
  
  implicit def refObsToObs[T](refObs:RefObservable[T]) = refObs.obs
  
  implicit def zip[A](list:js.Array[RefObservable[A]]) = Observable.zip(list.map(_.obs):_*)
  
  def filterAndSort[T,U](
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
}