package quizleague.web.site.competition

import quizleague.web.service.competition.CompetitionGetService
import quizleague.web.site.fixtures.FixturesService
import quizleague.web.site.leaguetable.LeagueTableService
import quizleague.web.site.text.TextService
import quizleague.web.site.venue.VenueService
import quizleague.web.site.season.SeasonService
import rxscalajs.Observable._
import rxscalajs.Observable
import quizleague.web.model.CompetitionType
import quizleague.web.core._
import com.felstar.scalajs.vue.Router
import quizleague.web.core.RouteConfig
import quizleague.web.model.Competition
import scalajs.js
import js.JSConverters._
import rxscalajs.Subject
import rxscalajs.subjects.ReplaySubject
import quizleague.web.model.Season
import quizleague.web.site.ApplicationContextService
import quizleague.web.site.season.SeasonWatchService
import java.time.LocalDate
import quizleague.web.model.Fixtures
import quizleague.util.collection._
import quizleague.web.site.season.SeasonService
import quizleague.web.util.Logging._
import scala.reflect.ClassTag
import scala.reflect.api.TypeTags

object CompetitionModule extends Module {
  
  override val routes = @@(
        RouteConfig(path="/competition/:id/league", components=Map("default" -> LeagueCompetitionPage, "title" -> CompetitionTitle, "sidenav" -> CompetitionMenu)),
        RouteConfig(path="/competition/:id/subsidiary", components=Map("default" -> BeerCompetitionPage, "title" -> CompetitionTitle, "sidenav" -> CompetitionMenu)),
        RouteConfig(path="/competition/:id/cup", components=Map("default" -> CupCompetitionPage, "title" -> CompetitionTitle, "sidenav" -> CompetitionMenu)),
        RouteConfig(path="/competition/:id/singleton", components=Map("default" -> SingletonCompetitionPage, "title" -> CompetitionTitle, "sidenav" -> CompetitionMenu)),
        RouteConfig(path="/competition/:id/results", components=Map("default" -> ResultsPage, "title" -> CompetitionResultsTitle, "sidenav" -> CompetitionMenu)),
        RouteConfig(path="/competition/:id/fixtures", components=Map("default" -> FixturesPage, "title" -> CompetitionFixturesTitle, "sidenav" -> CompetitionMenu)),

        RouteConfig(path="/competition", components=Map("default" -> CompetitionsComponent, "title" -> CompetitionsTitleComponent, "sidenav" -> CompetitionMenu)
  ))
}

object CompetitionService extends CompetitionGetService{
  
  override val fixturesService = FixturesService
  override val leagueTableService = LeagueTableService
  override val textService = TextService
  override val venueService = VenueService
  
  def firstClassCompetitions(seasonId:String) = competitions(seasonId).map(c => c.filter(_.typeName != CompetitionType.subsidiary.toString()))
  
 
  def competitionsOfType[T <: Competition:ClassTag](seasonId:String):Observable[js.Array[T]] = {
    competitions(seasonId).map(_ collect { case element: T => element })
  }
  
  def competition[T <: Competition:ClassTag](seasonId:String, typeName:String) = 
    competitionsOfType[T](seasonId).map(_.filter(_.typeName == typeName).head)
  
  def competitions(seasonId:String) = SeasonService.get(seasonId).flatMap(s => list(s.key))
}

object CompetitionViewService extends SeasonWatchService {
  
  def competitions() = season.flatMap(s => CompetitionService.list(s.key))
  
  def fixtures(competitionId:String) = CompetitionService
    .get(competitionId)
    .flatMap(c => FixturesService.list(c.key))
  
  def nextFixtures(competitionId:String, take:Integer = Integer.MAX_VALUE):Observable[js.Array[Fixtures]] = {
    val today = LocalDate.now.toString
    fixtures(competitionId).map(_.filter(_.date >= today).sortBy(_.date).take(take))
  }

  def latestResults(competitionId:String, take:Integer = Integer.MAX_VALUE):Observable[js.Array[Fixtures]] = {
    val today = LocalDate.now.toString
    fixtures(competitionId).map(_.filter(_.date <= today).sortBy(_.date)(Desc).take(take))
  }
  
  def parentSeason(competitionId:String) = {
    
    val seasons = SeasonService.list()
    
    seasons.map(_.filter(_.competitions.exists(_.id == competitionId)).headOption.getOrElse(null))
  }
}
