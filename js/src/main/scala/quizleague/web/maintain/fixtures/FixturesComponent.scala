package quizleague.web.maintain.fixtures

import angulate2.std._
import angulate2.router.ActivatedRoute
import angulate2.common.Location
import quizleague.web.maintain.component.ItemComponent
import quizleague.web.maintain.component._
import quizleague.web.model._
import scala.scalajs.js
import angulate2.ext.classModeScala
import TemplateElements._
import quizleague.web.maintain.text.TextService
import angulate2.router.Router
import js.Dynamic.{ global => g }
import quizleague.web.util.Logging
import quizleague.web.maintain.competition.CompetitionService
import quizleague.web.maintain.team.TeamService
import quizleague.web.model.Team
import quizleague.web.maintain.venue.VenueService
import rxjs.Observable
import rxjs.Observable


@Component(
  template = s"""
  <div>
    <h2>Fixtures</h2>
    <form #fm="ngForm" (submit)="save()">
    <div fxLayout="column">
      <div fxLayout="column">
        <md-input-container>
          <input  required mdInput placeholder="Date" name="date" [(ngModel)]="item.date" type="date">
        </md-input-container>
        <md-input-container>
          <input mdInput placeholder="Description" name="description" [(ngModel)]="item.description" type="text">
        </md-input-container>
        <md-input-container>
          <input required mdInput placeholder="Parent Description" name="parentDescription" [(ngModel)]="item.parentDescription" type="text">
        </md-input-container>
        <md-input-container>
          <input required mdInput placeholder="Start Time" name="time" [(ngModel)]="item.start" type="time">
        </md-input-container>
        <md-input-container>
          <input required mdInput placeholder="Duration" name="duration" [(ngModel)]="item.duration" type="number" step=".1">
        </md-input-container>
       </div>
       <div fxLayout="column">
        <h4>Fixture List</h4>
        <div fxLayout="row">          
          <md-select placeholder="Home" [(ngModel)]="homeTeam" name="homeTeam" (change)="setVenue(homeTeam)">  
            <md-option *ngFor="let team of unusedTeams()" [value]="team">{{team.name}}</md-option>
          </md-select>
          <md-select placeholder="Away" [(ngModel)]="awayTeam" name="awayTeam">  
            <md-option *ngFor="let team of unusedTeams()" [value]="team">{{team.name}}</md-option>
          </md-select>
          <md-select placeholder="Venue" [(ngModel)]="venue" name="venue">  
            <md-option *ngFor="let venue of venues" [value]="venue">{{venue.name}}</md-option>
          </md-select>
          <button md-icon-button type="button" (click)="addFixture()" [disabled]="!(homeTeam && awayTeam && venue)"><md-icon class="md-24">add</md-icon></button>
         </div>
         <div fxLayout="column">
          <div *ngFor="let fixture of item.fixtures">
            <span>{{fixture.home.name}} - {{fixture.away.name}} @ {{fixture.venue.name}}</span>
          </div>
         </div>
        </div>      
     </div>
     $formButtons
    </form>
  </div>
  """    
)
@classModeScala
class FixturesComponent(
    override val service:FixturesService,
    override val route: ActivatedRoute,
    override val location:Location,
    val router:Router,
    val competitionService:CompetitionService,
    val fixtureService:FixtureService,
    val teamService:TeamService,
    val venueService:VenueService)
    extends ItemComponent[Fixtures] with Logging{
  
    override def cancel():Unit = location.back()
    var comp:Competition = _
    var teamManager:TeamManager = _
    var homeTeam:Team = _
    var awayTeam:Team = _
    var venue:Venue = _
    var venues:js.Array[Venue] = _
    
    override def init(): Unit = {
      
      route.params    
      .switchMap( (params,i) => competitionService.get(params("competitionId")) )
      .subscribe(comp = _)
      teamService.list().subscribe( x => teamManager = new TeamManager(x))
    
      venueService.list.subscribe(venues = _)
      
//       Observable.zip(
//       loadItem,
//       teamService.list(),
//       (fix:Fixtures,teams:js.Array[Team]) => (fix,teams)).subscribe(
//          (fix:Fixtures,teams:js.Array[Team]) =>        {
//         log(fix, "Fixtures")
//
//         teamManager = new TeamManager(teams)
//         fix.fixtures.foreach({x => {teamManager.take(x.away);teamManager.take(x.home)}})
//         item = fix 
//       } 
//       )
 

      super.init()
      
    }
    
    override def save():Unit = {
      service.cache(item)
      item.fixtures.foreach({fixtureService.cache(_)})
      location.back()}

    def unusedTeams() = teamManager.unusedTeams
    
    def setVenue(team:Team) = {
      teamService.get(team.id).subscribe(x => venue = x.venue)
    }
    
    def addFixture() = {
      fixtureService.instance(
          item, 
          teamManager.take(homeTeam), 
          teamManager.take(awayTeam), 
          venue).subscribe(
            item.fixtures += _  
          )
 
      homeTeam = null
      awayTeam = null
      venue = null
    }
    
    class TeamManager(teams:js.Array[Team]){
      
      private val usedTeams = js.Array[Team]()
      
      def unusedTeams = teams.diff(usedTeams)
      
      def take(team:Team) = {usedTeams += team; team}
      def untake(team:Team) = usedTeams -= team
    }
}
    