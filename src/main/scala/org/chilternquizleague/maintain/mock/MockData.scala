package org.chilternquizleague.maintain.mock

import scala.scalajs.js
import scala.scalajs.js.annotation.JSExport
import scala.scalajs.js.annotation.ScalaJSDefined
import angulate2.ext.inMemoryWebApi.InMemoryDbService
import org.chilternquizleague.maintain.domain.Venue
import org.chilternquizleague.util.UUID
import json.AnyValJSEx


@JSExport
@ScalaJSDefined
class MockData extends InMemoryDbService {
  import json._
  override def createDb(): js.Any = js.Dictionary(
    "venue" -> js.Array(
      Venue(UUID.randomUUID().toString(), "wibble", "", "", "").js.toString,
      Venue(UUID.randomUUID().toString(), "wobble", "", "", "").js.toString
    ),
    "team" -> js.Array()
  )
}