package quizleague.web.mock

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
        literal(id ="1", json =  Venue("1", "The Squirrel", "Penn Street\nAmersham\nBuckinghamshire\nHP7 0PX",Option("12345"), Option("here@there.com"), Option("http://chilternquizleague.uk"), Option("http://storage.googleapis.com/seismic-bonfire-602.appspot.com/venue/5649391675244544/squirrel.jpg")).asJson.noSpaces),
        literal(id ="2", json =  Venue("2", "w0bble", "", None, None, None, None).asJson.noSpaces)
    ),
    "team" -> js.Array(
        literal(id ="1", json =  Team("1", "wibble arms", "wibble", Ref("venue","1"), Ref("text","1"), List(Ref("user","1"),Ref("user","2"))).asJson.noSpaces),
        literal(id ="2", json =  Team("2", "wobble villa", "wobble", Ref("venue","2"), Ref("text","5"), List()).asJson.noSpaces)
    ),
    "user" -> js.Array(
        literal(id ="1", json =  User("1", "me", "me@here.com").asJson.noSpaces),
        literal(id ="2", json =  User("2", "you", "you@there.com").asJson.noSpaces)
    ),
    "text" -> js.Array(
        literal(id ="1", json =  Text("1", "some text here", "text/plain").asJson.noSpaces),
        literal(id ="2", json =  Text("2", "<b>global text here</b>", "text/html").asJson.noSpaces),
        literal(id ="3", json =  Text("3", "a match report", "text/html").asJson.noSpaces),
        literal(id ="4", json =  Text("4","""<p>The Chiltern Quiz League is&nbsp;a friendly, local quiz league based in and around the Buckinghamshire towns of Amersham and Chesham (with an outpost in Farnham Common).</p>
<p>We currently have 10 teams in a single <a href="competitions/LEAGUE">league</a>, with all&nbsp;teams playing each other twice, home and away. We also run two knockout competitions, the&nbsp;<a href="competitions/CUP">Cup</a> and the&nbsp;<a href="competitions/PLATE">Plate</a> (think of the Plate as the Europa League to the Champions League of the Cup), and the just-for-fun&nbsp;<a href="competitions/BEER">Beer Leg</a> each night after the main quiz.</p>
<p>Our season normally runs from October to April; matches are played on Tuesday nights at 8:30pm, and generally last about 1&frac12; hours.</p>
<p>The best way to get a flavour of the league is to read some <a href="reports/all">match reports</a>.</p>""", "text/html").asJson.noSpaces),
        literal(id ="5", json =  Text("5", "<b>Some bold team text here</b>", "text/html").asJson.noSpaces),
        literal(id ="6", json =  Text("6", "Season text 2", "text/plain").asJson.noSpaces),
        literal(id ="7", json =  Text("7", "Individual Competition text", "text/html").asJson.noSpaces)
        


    ),
    "globalText" -> js.Array(
        literal(id ="1", json =  GlobalText("1", "default global text", Map(
        "a text entry" -> Ref("text", "2"),
        "front_page_main" -> Ref("text", "4")
        )).asJson.noSpaces)

    ),
    "applicationContext" -> js.Array(
      literal(id="1", json = ApplicationContext(
          "1",
          "Chiltern Quiz League",
          Ref[GlobalText]("globalText","1"), 
          Ref[Season]("season","1"),
          "a@b.c", 
          List(EmailAlias("webmaster@b.c", Ref[User]("user","1"))),
          "bucket name").asJson.noSpaces)

    ),
    "season" -> js.Array(literal(id="1", json = Season("1",
            Year.of(2017), 
            Year.of(2018),
            Ref[Text]("text","1"), 
            List(Ref[Competition]("competition","1"),Ref[Competition]("competition","2")),
            List[CalendarEvent](
                CalendarEvent(
                    Ref[Venue]("venue","1"),
                    LocalDate.parse("2017-03-01"),
                    LocalTime.of(20,30), 
                    Duration.ofMinutes(90),
                    "Individuals Qualification" ),
                    CalendarEvent(
                    Ref[Venue]("venue","1"),
                    LocalDate.parse("2017-06-06"),
                    LocalTime.of(20,30), 
                    Duration.ofMinutes(90),
                    "Presentation Night" ))
            ).asJson.noSpaces),
            literal(id="2", json = Season("2",
            Year.of(2016), 
            Year.of(2017),
            Ref[Text]("text","6"), 
            List(),
            List()).asJson.noSpaces)
        ),
      "competition" -> js.Array(literal(id="1", json=LeagueCompetition("1", 
          "League", 
          LocalTime.of(20,30), 
          Duration.ofMinutes(90),
          List(Ref[Fixtures]("fixtures","1"),Ref[Fixtures]("fixtures","2"),Ref[Fixtures]("fixtures","3")),
          List(Ref[Results]("results","1"),Ref[Results]("results","2")),
          List(Ref[LeagueTable]("leaguetable","1")),
          Ref[Text]("text","1"),
          None).asInstanceOf[Competition].asJson.noSpaces),
          literal(id="2", json=SingletonCompetition("2",
              "Indvidual Quiz",
              Option(Event(Ref[Venue]("venue","1"),
                    LocalDate.parse("2017-05-05"),
                    LocalTime.of(20,30), 
                    Duration.ofMinutes(90))),
              "individual_front_page",
              Ref[Text]("text","7")).asInstanceOf[Competition].asJson.noSpaces)  
      ),
      "fixtures" -> js.Array(literal(id="1", json=Fixtures("1",
          "Week 1",
          "League",
          LocalDate.parse("2017-03-01"),
          LocalTime.of(20,30), 
          Duration.ofMinutes(90),
          List(Ref[Fixture]("fixture","1"))).asJson.noSpaces),
          literal(id="2", json=Fixtures("2",
          "Week 2",
          "League",
          LocalDate.parse("2017-03-08"),
          LocalTime.of(20,30), 
          Duration.ofMinutes(90),
          List(Ref[Fixture]("fixture","2"))).asJson.noSpaces),
          literal(id="3", json=Fixtures("3",
          "Week 3",
          "League",
          LocalDate.parse("2017-11-08"),
          LocalTime.of(20,30), 
          Duration.ofMinutes(90),
          List(Ref[Fixture]("fixture","3"))).asJson.noSpaces)
      ),
      "fixture" -> js.Array(literal(id="1", json=Fixture("1",
          "",
          "League",
          Ref[Venue]("venue","1"),
          Ref[Team]("team", "1"),
          Ref[Team]("team", "2"),
          LocalDate.parse("2017-03-01"),
          LocalTime.of(20,30), 
          Duration.ofMinutes(90)).asJson.noSpaces),
          literal(id="2", json=Fixture("2",
          "",
          "League",
          Ref[Venue]("venue","2"),
          Ref[Team]("team", "2"),
          Ref[Team]("team", "1"),
          LocalDate.parse("2017-03-08"),
          LocalTime.of(20,30), 
          Duration.ofMinutes(90)).asJson.noSpaces),
          literal(id="3", json=Fixture("3",
          "",
          "League",
          Ref[Venue]("venue","2"),
          Ref[Team]("team", "2"),
          Ref[Team]("team", "1"),
          LocalDate.parse("2017-11-08"),
          LocalTime.of(20,30), 
          Duration.ofMinutes(90)).asJson.noSpaces)
      ),
      "results" -> js.Array(literal(id="1", json=Results("1",
          Ref[Fixtures]("fixtures","1"),
          List(Ref[Result]("result","1"))).asJson.noSpaces),
          literal(id="2", json=Results("2",
          Ref[Fixtures]("fixtures","2"),
          List(Ref[Result]("result","2"))).asJson.noSpaces)
      ),
      "result" -> js.Array(literal(id="1", json=Result("1",
          Ref[Fixture]("fixture","1"),
          80,70,
          Option(Ref[User]("user", "1")),
          "a note",
          List(Report(Ref[Team]("team", "1"), Ref[Text]("text","3")))
        ).asJson.noSpaces),
        literal(id="2", json=Result("2",
          Ref[Fixture]("fixture","2"),
          70,80,
          Option(Ref[User]("user", "1")),
          "",
          List()
        ).asJson.noSpaces)
      ),
      "leaguetable" -> js.Array(literal(id="1", json=LeagueTable("1", "Division 1", 
          List(LeagueTableRow(Ref[Team]("team","1"),"1",0,0,0,0,0,0,0),
              LeagueTableRow(Ref[Team]("team","2"),"2",0,0,0,0,0,0,0))).asJson.noSpaces))
   )
}
