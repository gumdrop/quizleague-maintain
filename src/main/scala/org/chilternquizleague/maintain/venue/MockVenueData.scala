package org.chilternquizleague.maintain.venue

import scala.scalajs.js
import scala.scalajs.js.annotation.JSExport
import scala.scalajs.js.annotation.ScalaJSDefined
import angulate2.ext.inMemoryWebApi.InMemoryDbService
import org.chilternquizleague.maintain.domain.Venue
import org.chilternquizleague.util.UUID


@JSExport
@ScalaJSDefined
class MockVenueData extends InMemoryDbService {
  import json._
  override def createDb(): js.Any = js.Dictionary("venue" -> js.Array(
    Venue(UUID.randomUUID().toString(), "wibble", null, null, null).js.toString,
    Venue(UUID.randomUUID().toString(), "wobble", null, null, null).js.toString
  ))
}