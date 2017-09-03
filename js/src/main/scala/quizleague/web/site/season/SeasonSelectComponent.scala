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

@Component(
  selector = "ql-season-select",
  template = """
    <md-select class="mat-body-1" (change)="seasonChanged()" [(ngModel)]="currentSeason" [compareWith]="compare">
      <md-option  *ngFor="let season of seasons | async" [value]="season"><ql-season-name [season]="obs(season)"></ql-season-name></md-option>
    </md-select>
""",
styles = @@@("""
  md-select{
    padding-left: .25em;
    position:relative;
    top:-3px;
  }
  md-select .mat-select-value-text span {
    font-size: 20px;
    font-weight: 500;
    color:  rgba(255,255,255,.87);

  }
  md-option {
    #color:black;
  }
""")

)  
class SeasonSelectComponent(
    seasonService:SeasonService){
  
  val seasons = seasonService.list.map((s,i) => s.sort((s1:Season,s2:Season) => s2.startYear compareTo s1.startYear))
   
  @Input
  var currentSeason:Season = _
  
  def seasonChanged() = onchange.emit(currentSeason)
    
  @Output
  val onchange = new EventEmitter[Season]()
  
  def obs(s:Season) = Observable.of(s)
  
  def compare(s1:js.Dynamic,s2:js.Dynamic) = s1 == s2 || ((s1 != null && s2 != null) && s1.id == s2.id)
 
}
