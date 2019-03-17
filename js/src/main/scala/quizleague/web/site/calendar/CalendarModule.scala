package quizleague.web.site.calendar

import quizleague.web.site.season.SeasonWatchService
import rxscalajs.subjects.BehaviorSubject

import scala.scalajs.js
import quizleague.web.model._
import rxscalajs.Observable
import rxscalajs.Observable.combineLatest
import js.JSConverters._
import quizleague.web.site.season.SeasonService
import quizleague.web.site.competition._
import quizleague.web.core._
import quizleague.web.core.RouteConfig


import quizleague.web.util.Logging._

object CalendarModule extends Module{
  override val routes = @@(RouteConfig(path="/calendar" , components=Map("default"->CalendarPage, "title"->CalendarTitleComponent)))
}

object CalendarViewService extends SeasonWatchService{
  
  var viewType:BehaviorSubject[String] = BehaviorSubject("timeline")

  def events(seasonId:String):Observable[js.Array[DateWrapper]] = {
    
    import CompetitionType._
    
    
    def singletonEvents(c:Competition):js.Array[EventWrapper] = c match {
      case s:SingletonCompetition => js.Array(EventWrapper(s.event,c))
      case _ => js.Array()
    }
    
    
    val comps = CompetitionService.firstClassCompetitions(seasonId)
    
    def fixtures = comps.flatMap(cs => combineLatest(cs.flatMap(c => c.fixtures.map(_.obs).map(f => f.map(EventWrapper(_,c)))).toSeq)).map(_.toJSArray)
    
    def singletons = comps.map(cs => cs.flatMap(singletonEvents _))
    
    def seasons = SeasonService.get(seasonId).map(s => s.calendar.map(e => EventWrapper(e)))

    combineLatest(Seq(fixtures,singletons,seasons))
        .map(lists =>  
          lists.flatMap(_.toSeq).toJSArray
          .groupBy(_.date)
          .map{case(d,e) => new DateWrapper(d, e)}
          .toJSArray
          .sortBy(_.date)
    )  
    

  }

  def allEvents():Observable[js.Dictionary[DateWrapper]] =
    SeasonService.list()
      .map(_.map(s => events(s.id)).toSeq)
      .flatMap(combineLatest _)
      .map(_.toJSArray.flatten)
      .map(_.map(x => (x.date, x)).toMap.toJSDictionary)


  def events:Observable[js.Array[DateWrapper]] = season.flatMap(s => events(s.id))
}




class DateWrapper(val date:String, val events:js.Array[EventWrapper]) extends js.Object

