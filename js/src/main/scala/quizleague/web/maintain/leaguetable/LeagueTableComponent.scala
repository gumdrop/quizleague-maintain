package quizleague.web.maintain.leaguetable

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
import quizleague.web.util.Logging._
import quizleague.web.maintain.team.TeamService
import quizleague.web.maintain.util.TeamManager
import quizleague.web.util.rx._
import rxjs.Observable
import quizleague.web.maintain.competition.CompetitionService


@Component(
 template = s"""
 <div>
    <h2>League Tables</h2>
    <form #fm="ngForm" (submit)="save()">
    <div fxLayout="column">
      <div fxLayout="column">
         <md-input-container>
          <input mdInput placeholder="Description" name="description" [(ngModel)]="item.description" type="text">
        </md-input-container>
       </div>
       <div fxLayout="column">
        <h4>Rows</h4>
        <div fxLayout="row">          
          <md-select placeholder="Home" [(ngModel)]="team" name="team">  
            <md-option *ngFor="let team of unusedTeams()" [value]="team">{{team.name}}</md-option>
          </md-select>
           <button md-icon-button type="button" (click)="addRow(team)" [disabled]="!team"><md-icon class="md-24">add</md-icon></button>
         </div>

         <div fxLayout="column">
         <div fxLayout="row"><button md-button type="button" (click)="recalculate()">Recalculate</button></div>
          <table>
            <thead>
              <th></th>
              <th>Team</th>
              <th>Position</th>
              <th>Won</th>
              <th>Lost</th>
              <th>Drawn</th>
              <th>Scored</th>
              <th>Against</th>
              <th>Points</th>
            </thead>
            <tbody>
              <tr *ngFor="let row of item.rows;let i = index">
                <td>
                  <button md-icon-button type="button" (click)="removeRow(row)" ><md-icon class="md-24">delete</md-icon></button>
                </td>
                <td>{{(row.team | async)?.name}}</td>
                <td>
                  <md-input-container>
                    <input mdInput [(ngModel)]="row.position" name="position{{i}}" type="text" length="2" >
                  </md-input-container>
                </td>
                <td>
                  <md-input-container>
                    <input mdInput [(ngModel)]="row.won" name="won{{i}}" type="number" length="2">
                  </md-input-container>
                </td>
                <td>
                  <md-input-container>
                    <input mdInput [(ngModel)]="row.lost" name="lost{{i}}" type="number" length="2">
                  </md-input-container>
                </td>
                <td>
                  <md-input-container>
                    <input mdInput [(ngModel)]="row.drawn" name="drawn{{i}}" type="number" length="2">
                  </md-input-container>
                </td>
                <td>
                  <md-input-container>
                    <input mdInput [(ngModel)]="row.matchPointsFor" name="matchPointsFor{{i}}" type="number" length="4">
                  </md-input-container>
                </td>
                <td>
                  <md-input-container>
                    <input mdInput [(ngModel)]="row.matchPointsAgainst" name="matchPointsAgainst{{i}}" type="number" length="4">
                  </md-input-container>
                </td>
                <td>
                  <md-input-container>
                    <input mdInput [(ngModel)]="row.leaguePoints" name="leaguePoints{{i}}" type="number" length="2">
                  </md-input-container>
                </td>
              </tr>
            </tbody>
          </table>
         </div>
        </div>      
     </div>
     $formButtons
    </form>
  </div>

  """ 
)
@classModeScala
class LeagueTableComponent(
    override val service:LeagueTableService,
    val teamService:TeamService,
    val competitionService:CompetitionService,
    override val route: ActivatedRoute,
    override val location:Location,
    val router:Router)
    extends ItemComponent[LeagueTable]{
  
    var teamManager:TeamManager =_
    var comp:Competition = _
  
    override def cancel():Unit = location.back()
    override def init() = {
     super.init()
     
     route.params
      .switchMap((params, i) => competitionService.get(params("competitionId")))
      .subscribe(comp = _)
     
     Observable.zip(
      loadItem()
        .switchMap((l, i) => zip(l.rows.map(_.team))),
      teamService.list(),
      (tableTeams: js.Array[Team], teams: js.Array[Team]) => {
        teamManager = new TeamManager(teams)
        tableTeams.foreach(teamManager.take(_))
      }).subscribe(x => Unit)

    }
    
    def removeRow(row:LeagueTableRow) = {
      item.rows -= row
      teamManager.untake(row.team)
    }
    def addRow(team:Team) = {
      item.rows += service.rowInstance(team)
      teamManager.take(team)
    }
    
    def recalculate() = {
      service.recalculateTable(item, comp).subscribe(item = _)
    }
    
    def unusedTeams() = if(teamManager == null) null else teamManager.unusedTeams(null)
    

  
}
    