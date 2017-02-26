package quizleague.web.maintain.mock

import scala.scalajs.js
import scala.scalajs.js.annotation.JSExport
import scala.scalajs.js.annotation.ScalaJSDefined
import angulate2.ext.inMemoryWebApi.InMemoryDbService
import quizleague.domain._
import quizleague.web.util.UUID
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
        literal(id ="1", json =  Team("1", "wibble arms", "wibble", Ref("venue","1"), Ref("text","1"), List(Ref("user","1"),Ref("user","2"))).asJson.noSpaces),
        literal(id ="2", json =  Team("2", "wobble villa", "wobble", Ref("venue","2"), Ref("text","1"), List()).asJson.noSpaces)
    ),
    "user" -> js.Array(
        literal(id ="1", json =  User("1", "me", "me@here.com").asJson.noSpaces),
        literal(id ="2", json =  User("2", "you", "you@there.com").asJson.noSpaces)
    ),
    "text" -> js.Array(
        literal(id ="1", json =  Text("1", "some text here", "text/plain").asJson.noSpaces),
        literal(id ="2", json =  Text("2", "global text here", "text/html").asJson.noSpaces)

    ),
    "globalText" -> js.Array(
        literal(id ="1", json =  GlobalText("1", "default global text", Map(
        "a text entry" -> Ref("text", "2")    
        )).asJson.noSpaces)

    ),
    "applicationContext" -> js.Array(
      literal(id="1", json = ApplicationContext(
          "1",
          "Chiltern Quiz League",
          Ref[GlobalText]("globalText","1"), 
          "a@b.c", 
          List(EmailAlias("webmaster@b.c", Ref[User]("user","1")))).asJson.noSpaces)    
    ),
    "season" -> js.Array(),
    "competition" -> js.Array(),
    "fixtures" -> js.Array(),
    "fixture" -> js.Array()
        
  )
}