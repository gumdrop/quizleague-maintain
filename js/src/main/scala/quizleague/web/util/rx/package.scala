package quizleague.web.util

package object rx {
  
  import scala.language.implicitConversions
  import scala.scalajs.js
  import rxjs.Observable
  
  implicit def refObsToObs[T](refObs:RefObservable[T]) = refObs.obs
  
  implicit def zip[A](list:js.Array[RefObservable[A]]) = Observable.zip(list.map(_.obs):_*)
}