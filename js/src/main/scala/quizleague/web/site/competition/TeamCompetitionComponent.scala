package quizleague.web.site.competition

import org.threeten.bp.LocalDate

import scala.scalajs.js

import quizleague.web.model.{ Fixtures, Results }
import scala.scalajs.js.annotation.JSExport
import quizleague.web.util.rx._
import quizleague.web.util.Logging._
import rxjs.Observable
import quizleague.web.model.Competition

trait TeamCompetitionComponent{
  
  this:BaseCompetitionComponent =>
  lazy val now = LocalDate.now.toString()
  
  @JSExport
  val textName:String
  
  @JSExport
  val latestResults = itemObs.switchMap((c,i) => sort2[Results,Fixtures](c.results, r => r.fixtures, (r1,r2) => r2._2.date compareTo r1._2.date, 1))
  
  
  
  @JSExport
  val nextFixtures = itemObs.switchMap((c,i) => filter[Fixtures](c.fixtures, f => f.date > now)).map((fs,i) => fs.sort((f1:Fixtures,f2:Fixtures) => f1.date compareTo f2.date).take(1))
  
  
}
