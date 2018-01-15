package quizleague.web.site.calendar

import quizleague.web.site.season.SeasonWatchService

//import angulate2.std._
//import angular.material.MaterialModule
//import angulate2.platformBrowser.BrowserModule
//import angulate2.router.{ Route, RouterModule }
//
import scala.scalajs.js
import quizleague.web.model._
//import angulate2.http.Http
//import quizleague.web.service.EntityService
//import angular.flexlayout.FlexLayoutModule
//import quizleague.web.util.UUID
//import quizleague.web.names.ComponentNames
//
//import angulate2.ext.classModeScala
//import angulate2.common.CommonModule
import rxscalajs.Observable
import rxscalajs.Observable.combineLatest
//import quizleague.web.service.venue.VenueGetService
//import quizleague.web.site._
//import quizleague.web.site.common.CommonAppModule
//import quizleague.web.site.text.TextModule
//import quizleague.web.site.global.ApplicationContextService
//import quizleague.web.site.common.SeasonSelectService
//import quizleague.web.site.season.SeasonModule
//import quizleague.web.model.Season
//import quizleague.web.site.season.SeasonService
import org.threeten.bp.LocalDate
//import quizleague.web.model.Competition
//import quizleague.web.model.SingletonCompetition
import js.JSConverters._
import scala.scalajs.js.annotation.JSExportAll
//import quizleague.web.site.results.ResultsComponentsModule
//import quizleague.web.site.fixtures.FixturesComponentsModule
//import quizleague.web.model._
//import quizleague.web.site.competition.CompetitionService
import quizleague.web.util.rx._
import quizleague.web.site.season.SeasonService
import quizleague.web.site.competition._
import quizleague.web.core._
import quizleague.web.core.RouteConfig


import quizleague.web.util.Logging._

object CalendarModule extends Module{
  override val routes = @@(RouteConfig(path="/calendar" , components=Map("default"->CalendarPage, "title"->CalendarTitleComponent)))
}

object CalendarViewService extends SeasonWatchService{
  
  def events(seasonId:String):Observable[js.Array[DateWrapper]] = {
    
    import CompetitionType._
    
    
    def singletonEvents(c:Competition):js.Array[EventWrapper] = c match {
      case s:SingletonCompetition => js.Array(EventWrapper(s.event,c))
      case _ => js.Array()
    }
    
    
    val comps = CompetitionService.firstClassCompetitions(seasonId)
    
    val fixtures:Observable[js.Array[EventWrapper]] = comps.flatMap(cs => combineLatest(cs.flatMap(c => c.fixtures.map(_.obs).map(f => f.map(EventWrapper(_,c)))).toSeq)).map(_.toJSArray)
    
    val singletons = comps.map(cs => cs.flatMap(singletonEvents _))
    
    val seasons = season.map(s => s.calendar.map(e => EventWrapper(e)))

    combineLatest(Seq(fixtures,singletons,seasons))
        .map(lists =>  
          lists.flatMap(_.toSeq).toJSArray
          .groupBy(_.date)
          .toIterable
          .map{case(d,e) => new DateWrapper(d, e)}
          .toJSArray
          .sortBy(_.date)
    )  
    

  }

  def events:Observable[js.Array[DateWrapper]] = season.flatMap(s => events(s.id))
}




class DateWrapper(val date:String, val events:js.Array[EventWrapper]) extends js.Object

