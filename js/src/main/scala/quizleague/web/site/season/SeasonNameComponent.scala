package quizleague.web.site.season

import angulate2.std._
import rxjs.Observable
import quizleague.web.model.Season

@Component(
   selector = "ql-season-name",
   template = """<span *ngIf="season | async as s">{{text(s)}}</span>"""
)
class SeasonNameComponent {
  
  @Input
  var season:Observable[Season] = _
  
  def text(s:Season) = if(s.startYear == s.endYear) s.startYear else s"""${s.startYear}/${s.endYear}""" 
  
}