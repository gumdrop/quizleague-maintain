package quizleague.web.site.fixtures

import quizleague.web.service.fixtures.FixtureGetService
import quizleague.web.service.fixtures.FixturesGetService
import quizleague.web.site.team.TeamService
import quizleague.web.site.venue.VenueService
import quizleague.web.model._
import quizleague.web.site.team.TeamService
import quizleague.web.core._
import scalajs.js
import js.JSConverters._
import rxscalajs.Observable
import rxscalajs.Observable._
import quizleague.web.model.CompetitionType
import org.threeten.bp.LocalDate
import quizleague.web.util.Logging._
import quizleague.web.site.season.SeasonService
import quizleague.web.site.competition.CompetitionService
import quizleague.web.site.user.UserService
import quizleague.util.collection._
import quizleague.web.site.text.TextService
import quizleague.web.service.results.ReportsGetService
import quizleague.web.service.PostService
import quizleague.domain.command.ResultsSubmitCommand
import quizleague.domain.command.ResultValues
import java.time.LocalTime

object FixturesModule extends Module {

  override val components = @@(SimpleFixturesComponent, AllFixturesComponent)
}

object FixturesService extends FixturesGetService {
  override val fixtureService = FixtureService

  def nextFixtures(seasonId: String): Observable[js.Array[Fixtures]] = activeFixtures(seasonId,1)
  def latestResults(seasonId:String): Observable[js.Array[Fixtures]] = spentFixtures(seasonId,1)
  
  def activeFixtures(seasonId: String, take:Int = Integer.MAX_VALUE) = {
    val today = LocalDate.now.toString()

    seasonFixtures(seasonId).map(_.filter(_.date >= today).sortBy(_.date).take(take))
  }
  
  def spentFixtures(seasonId: String, take:Int = Integer.MAX_VALUE) = {
    val today = LocalDate.now.toString()

    seasonFixtures(seasonId).map(_.filter(_.date <= today).sortBy(_.date)(Desc).take(take))
  }

  private def seasonFixtures(seasonId:String) = {
    competitionFixtures(CompetitionService.firstClassCompetitions(seasonId))
  }
  
  def competitionFixtures(competitions:Observable[js.Array[Competition]]):Observable[js.Array[Fixtures]] = {
      competitions.map(_.flatMap(_.fixtures.map(_.obs))).flatMap(o => combineLatest(o).map(_.toJSArray))
  }

}

object FixtureService extends FixtureGetService with PostService{
  override val venueService = VenueService
  override val teamService = TeamService
  override val userService = UserService
  override val reportsService = ReportsService

  def teamFixtures(teamId: String, seasonId: String, take:Int = Integer.MAX_VALUE): Observable[js.Array[Fixture]] = {
    
    val fixtures = FixturesService.activeFixtures(seasonId)
    
    fixturesFrom(fixtures, teamId, take)
  }
  
  private def fixturesFrom(fixtures:Observable[js.Array[Fixtures]], teamId:String, take:Int, sortOrder:Ordering[String] = Asc[String]) = {
    val tf = fixtures.flatMap(fx => combineLatest(fx.flatMap(_.fixtures).map(_.obs)))
    .map(_.filter(f => f.home.id == teamId || f.away.id == teamId).sortBy(_.date)(sortOrder))
      
    tf.map(_.take(take).toJSArray)
  }
  
  def teamResults(teamId: String, seasonId: String, take:Int = Integer.MAX_VALUE): Observable[js.Array[Fixture]] = {
    
    val fixtures = FixturesService.spentFixtures(seasonId)
    
    val tf = fixtures.switchMap(fx => combineLatest(fx.flatMap(_.fixtures).map(_.obs)))
    .map(_.filter(f => f.result != null && (f.home.id == teamId || f.away.id == teamId)).sortBy(_.date)(Desc))
      
    tf.map(_.take(take).toJSArray)
  }

  def fixturesForResultSubmission(email: String, seasonId: String): Observable[js.Array[Fixture]] = {

    val today = LocalDate.now.toString()
    val now = today + LocalTime.now().toString()

    teamService.teamForEmail(email)
      .map(
        _.map(
          team => fixturesFrom(FixturesService.competitionFixtures(CompetitionService.competitions(seasonId)), team.id, Integer.MAX_VALUE, Desc))).map(f => Observable.combineLatest(f.toSeq)
          .map(
            _.flatMap(x => x)
              .filter(f => (f.date + f.time) <= now)
              .groupBy(_.date)
              .toList
              .sortBy(_._1)(Desc)
              .take(1)
              .map { case (k, v) => v }
              .flatMap(x => x)
              .toJSArray)).flatten
  }
  

  def submitResult(fixtures:js.Array[Fixture], reportText:String, email:String) = {
    import quizleague.util.json.codecs.CommandCodecs._
    
    val cmd = ResultsSubmitCommand(fixtures.map(f => ResultValues(f.id, f.result.homeScore, f.result.awayScore)).toList, Option(reportText), email)
    
    command[String,ResultsSubmitCommand](List("results","submit"),Some(cmd)).subscribe(x => Unit)
  }
  
}

object ReportsService extends ReportsGetService {
  val textService = TextService
  val teamService = TeamService
}

