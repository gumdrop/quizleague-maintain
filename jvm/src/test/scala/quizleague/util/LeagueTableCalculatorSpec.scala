package quizleague.util;

import org.scalatest._

import quizleague.domain._
import org.threeten.bp._
import scala.collection.mutable.Map
import quizleague.conversions.RefConversions.StorageContext

class LeagueTableCalculatorSpec extends FlatSpec with Matchers {

  "A league table" should "be recalculated correctly" in {

    val team1 = Ref[Team]("team", "1")
    val team2 = Ref[Team]("team", "2")
    val team3 = Ref[Team]("team", "3")

    val venue1 = Ref[Venue]("venue", "1")

    val fixture1 = Fixture("1", "", "", venue1, team1, team2, LocalDate.now, LocalTime.now, Duration.ofHours(1))
    val fixture2 = Fixture("2", "", "", venue1, team2, team1, LocalDate.now, LocalTime.now, Duration.ofHours(1))

    val result1 = Result("1", Ref("fixture", fixture1.id), 10, 25, None, "", List())
    val result2 = Result("1", Ref("fixture", fixture2.id), 13, 21, None, "", List())

    val results1 = Results("1", Ref("fixtures", "1"), List(Ref("result", result1.id), Ref("result", result2.id)))

    val contextMap = Map[String, Entity]()

    def addToContext[T <: Entity](a: T) { contextMap += ((a.getClass.getName + a.id, a)) }

    addToContext(fixture1)
    addToContext(fixture2)
    addToContext(result1)
    addToContext(result2)

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
    row1.leaguePoints should be(2)
    row1.team.id should be(team2.id)
  }
}
