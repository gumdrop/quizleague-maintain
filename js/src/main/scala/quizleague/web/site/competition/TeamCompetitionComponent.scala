package quizleague.web.site.competition

import java.time.LocalDate

import scala.scalajs.js

import angulate2.std._
import quizleague.web.model.{ Fixtures, Results }
import scala.scalajs.js.annotation.JSExport

trait TeamCompetitionComponent{
  
  @JSExport
  def latestResults(results:js.Array[Results]) = firstElement(results.sort((r1:Results,r2:Results) => r2.fixtures.date compareTo r1.fixtures.date))
  
  @JSExport
  def nextFixtures(fixtures:js.Array[Fixtures]) = {
    val now = LocalDate.now().toString()
    firstElement(fixtures.filter((f:Fixtures) => f.date > now).sort((f1:Fixtures,f2:Fixtures) => f1.date compareTo f2.date))
  }
  
  protected def firstElement[T](arr:js.Array[T]):js.Array[T] = {
    if(arr.isEmpty) js.Array() else @@@(arr.head)
  }
   
}