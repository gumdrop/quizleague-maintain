package quizleague.web.maintain.mock

import scala.scalajs.js
import scala.scalajs.js.annotation.JSExport
import scala.scalajs.js.annotation.ScalaJSDefined
import angulate2.ext.inMemoryWebApi.InMemoryDbService
import quizleague.domain._
import quizleague.web.util.UUID
import js.Dynamic.literal
import java.time.LocalDate
import java.time.LocalTime
import java.time.Duration
import quizleague.util.json.codecs.DomainCodecs._
import quizleague.util.json.codecs.ScalaTimeCodecs._
import java.time.Year


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
        literal(id ="2", json =  Text("2", "global text here", "text/html").asJson.noSpaces),
        literal(id ="3", json =  Text("3", "a match report", "text/html").asJson.noSpaces)


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
    "season" -> js.Array(literal(id="1", json = Season("1",
            Year.of(2017), 
            Year.of(2018),
            Ref[Text]("text","1"), 
            List(Ref[Competition]("competition","1")),
            List[CalendarEvent]()).asJson.noSpaces)
        ),
      "competition" -> js.Array(literal(id="1", json=LeagueCompetition("1", 
          "League", 
          LocalTime.of(20,30), 
          Duration.ofMinutes(90),
          List(Ref[Fixtures]("fixtures","1")),
          List(Ref[Results]("results","1")),
          List(Ref[LeagueTable]("leaguetable","1")),
          Ref[Text]("text","1"),
          None).asInstanceOf[Competition].asJson.noSpaces)
      ),
      "fixtures" -> js.Array(literal(id="1", json=Fixtures("1",
          "",
          "League",
          LocalDate.parse("2017-03-01"),
          LocalTime.of(20,30), 
          Duration.ofMinutes(90),
          List(Ref[Fixture]("fixture","1"))).asJson.noSpaces)
      ),
      "fixture" -> js.Array(literal(id="1", json=Fixture("1",
          "",
          "League",
          Ref[Venue]("venue","1"),
          Ref[Team]("team", "1"),
          Ref[Team]("team", "2"),
          LocalDate.parse("2017-03-01"),
          LocalTime.of(20,30), 
          Duration.ofMinutes(90)).asJson.noSpaces)
      ),
      "results" -> js.Array(literal(id="1", json=Results("1",
          Ref[Fixtures]("fixtures","1"),
          List(Ref[Result]("result","1"))).asJson.noSpaces)
      ),
      "result" -> js.Array(literal(id="1", json=Result("1",
          Ref[Fixture]("fixture","1"),
          80,70,
          Ref[User]("user", "1"),
          "a note",
          List(Report(Ref[Team]("team", "1"), Ref[Text]("text","3")))
        ).asJson.noSpaces)
      ),
      "leaguetable" -> js.Array(literal(id="1", json=LeagueTable("1", "A Table", List()).asJson.noSpaces))
   )
}
