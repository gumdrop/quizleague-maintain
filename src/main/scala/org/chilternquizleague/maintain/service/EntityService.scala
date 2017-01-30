package org.chilternquizleague.maintain.service

import rxjs.RxPromise
import rxjs.Observable
import angulate2.http._
import scala.scalajs.js
import js.JSConverters._
import js.ArrayOps
import scala.scalajs.js.annotation.ScalaJSDefined
import org.chilternquizleague.maintain.component.IdStuff
import org.chilternquizleague.maintain.domain.Entity
import org.chilternquizleague.util._
import org.chilternquizleague.maintain.domain.Ref
import org.chilternquizleague.maintain.component.ComponentNames

trait EntityService[T] extends GetService[T] with PutService[T]{
    this:ComponentNames =>
}
