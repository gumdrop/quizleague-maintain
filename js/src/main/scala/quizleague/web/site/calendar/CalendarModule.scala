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
import quizleague.web.model._
import quizleague.web.site.competition.CompetitionService
import quizleague.web.util.rx._

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
  seasonService:SeasonService,
  competitionService:CompetitionService
) extends SeasonSelectService{
  
  def getEvents(season:Season) = {
    
    import quizleague.web.model.CompetitionType._
    
    val now = LocalDate.now().toString
    
    def singletonEvents(c:Competition):js.Array[EventWrapper] = c match {
      case s:SingletonCompetition => js.Array(EventWrapper(s.event,c))
      case _ => js.Array()
    }
    
    val comps = zip(season.competitions)
    
    val results = comps
      .map((cs,i) => 
        cs.map(c => (c,c.results)).map(cr => extract1[Results,Fixtures,EventWrapper](cr._2, (r:Results) => r.fixtures)((r,f) => EventWrapper(r,f.date,cr._1))
          ))
      //.switchMap((cr,i) => extract1[Results,Fixtures,EventWrapper](cr._2, _.fixtures)((r,f) => EventWrapper(r,f.date,cr._1)))
    
    results
      
      
//     Observable.zip(season.competitions.map(_.obs):_*).map(
//          (c,i) => 
//          Observable.zip(c.filter(_.typeName != subsidiary.toString).flatMap(c => c.results)).map((x,i) => EventWrapper(x,c)) ++
//          c.flatMap(c => c.fixtures.filter(_.date > now).map(EventWrapper(_,c))) ++
//          c.flatMap(singletonEvents _) ++
//          season.calendar.map(e => EventWrapper(e)))
//          .groupBy(_.date)
//          .toIterable
//          .map(t => new DateWrapper(t._1, t._2))
//          .toJSArray
//          .sort((d1:DateWrapper,d2:DateWrapper) => d1.date compareTo d2.date))

  }
  
}

@JSExportAll
class DateWrapper(val date:String, val events:js.Array[EventWrapper])

