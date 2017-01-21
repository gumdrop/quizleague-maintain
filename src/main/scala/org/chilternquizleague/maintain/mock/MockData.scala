package org.chilternquizleague.maintain.mock

import scala.scalajs.js
import scala.scalajs.js.annotation.JSExport
import scala.scalajs.js.annotation.ScalaJSDefined
import angulate2.ext.inMemoryWebApi.InMemoryDbService
import org.chilternquizleague.maintain.domain.Venue
import org.chilternquizleague.util.UUID
import json.AnyValJSEx
import js.Dynamic.literal


@JSExport
@ScalaJSDefined
class MockData extends InMemoryDbService {
  import json._
  override def createDb(): js.Any = js.Dictionary(
    "venue" -> js.Array(
        literal(id ="1", json =  Venue("1", "wibble", None, None, None).js.toJSONString),
        literal(id ="2", json =  Venue("2", "w0bble", None, None, None).js.toJSONString)
    ),
    "team" -> js.Array()
  )
}