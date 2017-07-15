package quizleague.util;

import org.scalatest._

import quizleague.domain._
import org.threeten.bp._
import scala.collection.mutable.Map
import quizleague.conversions.RefConversions.StorageContext
import quizleague.util.conversions.Conversions._
import java.util.UUID.{randomUUID => uuid}


class LeagueTableCalculatorSpec extends FlatSpec with Matchers {

  "A league table" should "be recalculated correctly" in {

    val team1 = Ref[Team]("team", uuid)
    val team2 = Ref[Team]("team", uuid)
    val team3 = Ref[Team]("team", uuid)

    val venue1 = Ref[Venue]("venue", uuid)

    val fixture1 = Fixture(uuid, "", "", venue1, team1, team2, LocalDate.now, LocalTime.now, Duration.ofHours(1))
    val fixture2 = Fixture(uuid, "", "", venue1, team2, team1, LocalDate.now, LocalTime.now, Duration.ofHours(1))
    val fixture3 = Fixture(uuid, "", "", venue1, team2, team1, LocalDate.now, LocalTime.now, Duration.ofHours(1))

    val result1 = Result(uuid, Ref("fixture", fixture1.id), 10, 25, None, "", List())
    val result2 = Result(uuid, Ref("fixture", fixture2.id), 13, 21, None, "", List())
    val result3 = Result(uuid, Ref("fixture", fixture3.id), 11, 11, None, "", List())

    val results1 = Results(uuid, Ref("fixtures", "1"), List(Ref("result", result1.id), Ref("result", result2.id), Ref("", result3.id)))

    val contextMap = Map[String, Entity]()

    def addToContext[T <: Entity](a: T) { contextMap += ((a.getClass.getName + a.id, a)) }

    addToContext(fixture1)
    addToContext(fixture2)
    addToContext(fixture3)
    addToContext(result1)
    addToContext(result2)
    addToContext(result3)

    implicit val context = StorageContext(contextMap)

    val table = LeagueTable(
      "1",
      "",
      List(
        LeagueTableRow(
          team1,
          "",
          0, 0, 0, 0, 0, 0, 0),
        LeagueTableRow(
          team2,
          "",
          0, 0, 0, 0, 0, 0, 0)))

    val rectab = LeagueTableCalculator.recalculate(table, List(results1))

    println(rectab)
    
    rectab.rows.size should be(2)

    val row1 = rectab.rows.head
    val row2 = rectab.rows.tail.head
    row1.leaguePoints should be(3)
    row1.played should be (3)
    row1.position should be ("1")
    row1.team.id should be(team2.id)
    row1.won should be (1)
    row1.drawn should be (1)
    row1.lost should be (1)
    row1.matchPointsFor should be (49)
    row1.matchPointsAgainst should be (42)
    
    row2.leaguePoints should be(3)
    row2.played should be (3)
    row2.position should be ("2")
    row2.team.id should be(team1.id)
    row2.won should be (1)
    row2.drawn should be (1)
    row2.lost should be (1)
    row2.matchPointsFor should be (42)
    row2.matchPointsAgainst should be (49)
    
  }
}
