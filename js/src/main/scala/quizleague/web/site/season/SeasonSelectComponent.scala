package quizleague.web.site.season

import angulate2.std._
import quizleague.web.model.Season
import angulate2.core.EventEmitter
import angulate2.ext.classModeScala
import quizleague.web.util.Logging
import rxjs.Subject
import scala.scalajs.js
import angulate2.core.ElementRef
import rxjs.Observable
import quizleague.web.util.Logging._
import quizleague.util.collection._

@Component(
  selector = "ql-season-select",
  template = """
    <md-menu #seasonsMenu="mdMenu" fxLayout="column">
  <button md-menu-item *ngFor="let season of seasons | async" (click)="seasonChanged(season)"><ql-season-name [season]="obs(season)"></ql-season-name></button>
</md-menu>

<span [mdMenuTriggerFor]="seasonsMenu">
   <ql-season-name [season]="obs(currentSeason)"></ql-season-name>
</span>
  
""",
styles = @@@("""
  span{
    padding-left: .25em;
    cursor:pointer;
  }
 """)

)  
class SeasonSelectComponent(
    seasonService:SeasonService){
  
  val seasons = seasonService.list.map((s,i) => s.sortBy(_.startYear)(Desc))
   
  @Input
  var currentSeason:Season = _
  
  def seasonChanged(season:Season) ={currentSeason = season; onchange.emit(currentSeason)}
    
  @Output
  val onchange = new EventEmitter[Season]()
  
  def obs(s:Season) = Observable.of(s)
  
  def compare(s1:js.Dynamic,s2:js.Dynamic) = s1 == s2 || ((s1 != null && s2 != null) && s1.id == s2.id)
 
}
