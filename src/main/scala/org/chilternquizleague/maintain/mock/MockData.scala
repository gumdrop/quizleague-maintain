package org.chilternquizleague.maintain.mock

import scala.scalajs.js
import scala.scalajs.js.annotation.JSExport
import scala.scalajs.js.annotation.ScalaJSDefined
import angulate2.ext.inMemoryWebApi.InMemoryDbService
import org.chilternquizleague.maintain.domain._
import org.chilternquizleague.util.UUID
import js.Dynamic.literal


@JSExport
@ScalaJSDefined
class MockData extends InMemoryDbService {
  import io.circe._, io.circe.generic.auto._, io.circe.parser._, io.circe.syntax._

  override def createDb(): js.Any = js.Dictionary(
    "venue" -> js.Array(
        literal(id ="1", json =  Venue("1", "wibble", None, None, None, None).asJson.noSpaces),
        literal(id ="2", json =  Venue("2", "w0bble", None, None, None, None).asJson.noSpaces)
    ),
    "team" -> js.Array(
        literal(id ="1", json =  Team("1", "wibble arms", "wibble", Ref("venue","1"), List(Ref("user","1"),Ref("user","2"))).asJson.noSpaces)
    ),
    "user" -> js.Array(
        literal(id ="1", json =  User("1", "me", "me@here.com").asJson.noSpaces),
        literal(id ="2", json =  User("2", "you", "you@there.com").asJson.noSpaces)
    )
  )
}