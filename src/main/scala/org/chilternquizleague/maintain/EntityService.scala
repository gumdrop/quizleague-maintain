package org.chilternquizleague.maintain


import angulate2._
import angulate2.core._
import angulate2.http.Http
import rxjs.RxPromise

import scala.scalajs.js

@Injectable
class EntityService(http:Http) {

  def get(id:String, name:String) = http.get(s"$name/$id").toPromise

}