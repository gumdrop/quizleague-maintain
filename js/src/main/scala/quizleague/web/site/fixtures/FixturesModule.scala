package quizleague.web.site.fixtures

import angulate2.ext.classModeScala
import angulate2.http.Http
import angulate2.std._
import quizleague.web.service.fixtures.FixtureGetService
import quizleague.web.service.fixtures.FixturesGetService
import quizleague.web.site.ServiceRoot
import quizleague.web.site.team.TeamService
import quizleague.web.site.venue.VenueService
import angulate2.common.CommonModule
import angular.material.MaterialModule
import angular.flexlayout.FlexLayoutModule
import quizleague.web.site.common.CommonAppModule


@NgModule(
  imports = @@[CommonModule, MaterialModule, FlexLayoutModule, CommonAppModule, FixturesComponentsModule],
  declarations = @@[AllFixturesComponent, AllFixturesTitleComponent],
  providers = @@[FixturesService, FixtureService],
  exports = @@[AllFixturesComponent, AllFixturesTitleComponent])
class FixturesModule


@NgModule(
  imports = @@[CommonModule],
  declarations = @@[SimpleFixturesComponent],
  exports = @@[SimpleFixturesComponent]
  )
class FixturesComponentsModule


@Injectable
@classModeScala
class FixturesService(override val http: Http,
    override val fixtureService: FixtureService) extends FixturesGetService with ServiceRoot {

}

@Injectable
@classModeScala
class FixtureService(override val http: Http,
    override val venueService: VenueService,
    override val teamService: TeamService) extends FixtureGetService with ServiceRoot {

}

