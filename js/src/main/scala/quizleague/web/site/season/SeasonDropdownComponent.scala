package quizleague.web.site.season

import angulate2.std._

@Component(
  selector = "ql-season-select",
  template = """
<select>
  <option *ngFor="let season of seasons | async">{{season.startYear}} / {{season.endYear}}</option>
</select>"""
)    
class SeasonDropdownComponent(
    seasonService:SeasonService) {
  
  val seasons = seasonService.list
  
}