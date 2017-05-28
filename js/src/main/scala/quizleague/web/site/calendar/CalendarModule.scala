package quizleague.web.site.calendar

import angulate2.std._
import angular.material.MaterialModule
import angulate2.platformBrowser.BrowserModule
import angulate2.router.{ Route, RouterModule }

import scala.scalajs.js
import quizleague.web.model.Venue
import angulate2.http.Http
import quizleague.web.service.EntityService
import angular.flexlayout.FlexLayoutModule
import quizleague.web.util.UUID
import quizleague.web.names.ComponentNames

import angulate2.ext.classModeScala
import angulate2.common.CommonModule
import rxjs.Observable
import quizleague.web.service.venue.VenueGetService
import quizleague.web.site._
import quizleague.web.site.common.CommonAppModule
import quizleague.web.site.text.TextModule
import quizleague.web.site.global.ApplicationContextService
import quizleague.web.site.common.SeasonSelectService
import quizleague.web.site.season.SeasonModule
import quizleague.web.model.Season
import quizleague.web.site.season.SeasonService
import java.time.LocalDate
import quizleague.web.model.Competition
import quizleague.web.model.SingletonCompetition
import js.JSConverters._
import scala.scalajs.js.annotation.JSExportAll
import quizleague.web.site.results.ResultsComponentsModule
import quizleague.web.site.fixtures.FixturesComponentsModule
import quizleague.web.model.Fixtures

@NgModule(
  imports = @@[CommonModule, MaterialModule, RouterModule, FlexLayoutModule, CommonAppModule, SeasonModule, CalendarRoutesModule, ResultsComponentsModule, FixturesComponentsModule],
  declarations = @@[CalendarComponent, CalendarTitleComponent, ResultsEventComponent, FixturesEventComponent, CalendarEventComponent, CompetitionEventComponent],
  providers = @@[CalendarViewService])
class CalendarModule

@Routes(
  root = false,
  Route(
    path = "calendar",
    children = @@@(
      Route("", component = %%[CalendarComponent]),
      Route("", component = %%[CalendarTitleComponent], outlet = "title")
    )))
class CalendarRoutesModule

@Injectable
@classModeScala
class CalendarViewService(
  override val applicationContextService:ApplicationContextService,
  seasonService:SeasonService
) extends SeasonSelectService{
  
  def getEvents(season:Season) = {
    
    import quizleague.web.model.CompetitionType._
    
    val now = LocalDate.now().toString
    
    def singletonEvents(c:Competition):js.Array[EventWrapper] = c match {
      case s:SingletonCompetition => js.Array(EventWrapper(s.event,c))
      case _ => js.Array()
    }
    
    seasonService.get(season.id)(3).map(
        (s,i) => 
          (s.calendar.map(e => EventWrapper(e)) ++
          s.competitions.filter(_.typeName != subsidiary.toString).flatMap(c => c.results.map(EventWrapper(_,c))) ++
          s.competitions.flatMap(c => c.fixtures.filter(_.date > now).map(EventWrapper(_,c))) ++
          s.competitions.flatMap(singletonEvents _))
          .groupBy(_.date)
          .toIterable
          .map(t => new DateWrapper(t._1, t._2))
          .toJSArray
          .sort((d1:DateWrapper,d2:DateWrapper) => d1.date compareTo d2.date)
        )
    
  }
  
}

@JSExportAll
class DateWrapper(val date:String, val events:js.Array[EventWrapper])

