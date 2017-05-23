package quizleague.web.site.season

import angulate2.std._
import quizleague.web.model.Season
import angulate2.core.EventEmitter
import angulate2.ext.classModeScala
import quizleague.web.util.Logging
import rxjs.BehaviorSubject
import scala.scalajs.js.annotation.JSExport
import angulate2.core.ElementRef
import rxjs.Observable

@Component(
  selector = "ql-season-select",
  template = """
    <select (change)="seasonChanged($event.target.value)" [value]="(currentSeason | async).id">
      <option  *ngFor="let season of seasons | async" [value]="season.id"><ql-season-name [season]="asObs(season)"></ql-season-name></option>
    </select>
""",
styles = @@@("""
  select {
    font-size: 20px;
    font-weight: 500;
    font-family: Roboto,"Helvetica Neue",sans-serif;
    background-color: transparent;
    border: none;
    color:  rgba(255,255,255,.87);
    padding-left: .25em;
  }
  option {
    color:black;
  }
""")

)  
class SeasonSelectComponent(
    seasonService:SeasonService){
  
  val seasons = seasonService.list
  
  @Input
  var currentSeason:BehaviorSubject[Season] = _
  
  def seasonChanged(id:String) = seasonService.get(id).subscribe(s => currentSeason.next(s))
  
  def asObs(s:Season) = Observable.of(s)
 
}