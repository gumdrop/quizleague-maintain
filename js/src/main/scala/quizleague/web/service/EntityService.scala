package quizleague.web.service

import rxjs.RxPromise
import rxjs.Observable
import angulate2.http._
import scala.scalajs.js
import js.JSConverters._
import js.ArrayOps
import scala.scalajs.js.annotation.ScalaJSDefined

import quizleague.domain.Entity
import quizleague.web.util._
import quizleague.domain.Ref
import quizleague.web.maintain.component.ComponentNames

trait EntityService[T] extends GetService[T] with PutService[T]{
    this:ComponentNames =>
}
