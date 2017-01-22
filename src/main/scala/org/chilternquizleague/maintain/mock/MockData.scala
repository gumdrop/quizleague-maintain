package org.chilternquizleague.maintain.mock

import scala.scalajs.js
import scala.scalajs.js.annotation.JSExport
import scala.scalajs.js.annotation.ScalaJSDefined
import angulate2.ext.inMemoryWebApi.InMemoryDbService
import org.chilternquizleague.maintain.domain.Venue
import org.chilternquizleague.util.UUID
import js.Dynamic.literal


@JSExport
@ScalaJSDefined
class MockData extends InMemoryDbService {
  import io.circe._, io.circe.generic.auto._, io.circe.parser._, io.circe.syntax._

  override def createDb(): js.Any = js.Dictionary(
    "venue" -> js.Array(
        literal(id ="1", json =  Venue("1", "wibble", None, None, None).asJson.noSpaces),
        literal(id ="2", json =  Venue("2", "w0bble", None, None, None).asJson.noSpaces)
    ),
    "team" -> js.Array()
  )
}