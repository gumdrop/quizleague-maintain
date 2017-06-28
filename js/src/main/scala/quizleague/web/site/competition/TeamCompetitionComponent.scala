package quizleague.web.site.competition

import java.time.LocalDate

import scala.scalajs.js

import quizleague.web.model.{ Fixtures, Results }
import scala.scalajs.js.annotation.JSExport
import quizleague.web.util.rx._

trait TeamCompetitionComponent{
  
  @JSExport
  val textName:String
  
  @JSExport
  def latestResults(results:js.Array[Results]) = sort[Results,Fixtures](results, r => r.fixtures, (r1,r2) => r2._2.date compareTo r1._2.date, 1)
  
  @JSExport
  def nextFixtures(fixtures:js.Array[Fixtures]) = {
    val now = LocalDate.now().toString()
    fixtures.filter((f:Fixtures) => f.date > now).sort((f1:Fixtures,f2:Fixtures) => f1.date compareTo f2.date).take(1)
  }
  
}