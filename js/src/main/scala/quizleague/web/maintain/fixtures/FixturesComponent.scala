package quizleague.web.maintain.fixtures



import quizleague.web.maintain.component._
import quizleague.web.model._

import scala.scalajs.js
import TemplateElements._
import quizleague.web.maintain.text.TextService

import js.Dynamic.{global => g}
import quizleague.web.util.Logging
import quizleague.web.maintain.competition.CompetitionService
import quizleague.web.maintain.team.TeamService
import quizleague.web.model.Team
import quizleague.web.maintain.venue.VenueService
import quizleague.web.maintain.util.TeamManager
import quizleague.web.util.rx._
import quizleague.web.maintain.competition.CompetitionComponentConfig
import quizleague.web.maintain.competition.CompetitionComponent
import quizleague.web.util.rx.RefObservable
import quizleague.web.util.rx.RefObservable
import quizleague.web.maintain.util.TeamManager
import quizleague.web.maintain.component.ItemComponentConfig._
import quizleague.web.core._
import com.felstar.scalajs.vue.VueRxComponent
import quizleague.web.util.component.{SelectUtils, SelectWrapper}



@js.native
trait FixturesComponent extends CompetitionComponent{
  var teamManager:TeamManager
  val teams:js.Array[SelectWrapper[Team]]
  var homeTeam:RefObservable[Team]
  var awayTeam:RefObservable[Team]
  val fxs:Fixtures
  var venue:RefObservable[Venue]
}

object FixturesComponent extends CompetitionComponentConfig{

  override type facade = FixturesComponent
  override def parentKey(c:facade) = s"season/${c.$route.params("seasonId")}/competition/${c.$route.params("id")}"

  val fixtureService = FixtureService
  components(FixtureComponent)
  
  val template = s"""
  <v-container v-if="item && season && fxs && teams && fixtures && venues">
    <h2>Fixtures : {{item.name}}</h2>
    <v-form v-model="valid">
    <v-layout column>
      <v-layout column>
        <v-text-field label="Date" v-model="fxs.date" type="date" required :rules=${valRequired("Date")}></v-text-field>
        <v-text-field label="Time" v-model="fxs.start" type="time" required :rules=${valRequired("Time")}></v-text-field>
        <v-text-field label="Duration" v-model.number="fxs.duration" type="number" step=".5" required :rules=${valRequired("Duration")}></v-text-field>
        <v-text-field label="Description" v-model="fxs.description" type="text" required :rules=${valRequired("Description")}></v-text-field>
        <v-text-field label="Parent Description" v-model="fxs.parentDescription" type="text"></v-text-field>
       </v-layout>
       <v-layout column>
        <h4>Fixture List</h4>
        <v-layout row v-if="venues && teams">
          <v-select label="Home" v-model="homeTeam" :items="unusedTeams(awayTeam)" @input="setVenue(homeTeam)"></v-select>        
          <v-select label="Away" v-model="awayTeam" :items="unusedTeams(homeTeam)"></v-select>
          <v-select label="Venue" v-model="venue" :items="venues"></v-select>
          <v-btn style="top:5px;" icon v-on:click="addFixture()" :disabled="!(homeTeam && awayTeam && venue)"><v-icon >mdi-plus</v-icon></v-btn>
         </v-layout>
         <v-layout column>
          <v-layout row  v-for="fixture in fixtures" :key="fixture.id">
           <v-btn style="top:-14px;" icon v-on:click="removeFixture(fixture)" ><v-icon>mdi-cancel</v-icon></v-btn>
           <fixture :fixture="fixture" :fixtures="fxs" :teamManager="teamManager"></fixture>
          </v-layout>
         </v-layout>
        </v-layout>      
     </v-layout>
     $formButtons
    </v-form>
  </v-container>
  """


  def unusedTeams(c:facade, other: RefObservable[Team]) = teamManager(c).unusedTeams(other)

  def setVenue(c:facade, team: RefObservable[Team]) = {
    TeamService.get(team.id).subscribe(t => c.venue = t.venue)
  }

  def addFixture(c:facade) = {
    val f = fixtureService.instance(
      c.fxs,
      teamManager(c).take(c.homeTeam), 
      teamManager(c).take(c.awayTeam),
      c.venue,
      c.item.typeName == CompetitionType.subsidiary.toString)
      fixtureService.save(f).subscribe(x => c.$forceUpdate())

    c.homeTeam = null
    c.awayTeam = null
    c.venue = null
  }

  def removeFixture(c:facade, fx:Fixture) = {
    if(org.scalajs.dom.window.confirm("Delete ?")){
      c.teamManager.untake(fx.home)
      c.teamManager.untake(fx.away)
      fixtureService.delete(fx).subscribe(x => c.$forceUpdate())
    }

  }
  
  def teamManager(c:facade) = if(c.teamManager == null) {c.teamManager = new TeamManager(c.teams); c.teamManager} else c.teamManager

 
  def venues() = SelectUtils.model[Venue](VenueService)(_.name)
  def teams() = SelectUtils.model[Team](TeamService)(_.name)
  override def save(c:facade) = {FixturesService.save(c.fxs);super.save(c)}
  
  method("addFixture")({addFixture _ }:js.ThisFunction)
  method("setVenue")({setVenue _ }:js.ThisFunction)
  method("unusedTeams")({unusedTeams _ }:js.ThisFunction)
  method("save")({save _ }:js.ThisFunction)
  method("removeFixture")({removeFixture _ }:js.ThisFunction)
  
  subscription("fxs")(c => obsFromParam(c,"fixturesId", FixturesService))
  subscription("fixtures")(c => obsFromParam(c,"fixturesId", FixturesService).flatMap(_.fixture))
  subscription("venues")(c => venues())
  subscription("teams")(c => teams())
  
  data("teamManager", null) 
  data("venue", null) 
  data("homeTeam", null) 
  data("awayTeam", null) 

}

@js.native
trait FixtureComponent extends VueRxComponent{
  val fixture:Fixture
  var fx:Fixture
  val fixtures:Fixtures
  val teamManager:TeamManager
  var showResult:Boolean
  
}

object FixtureComponent extends Component{
  
  type facade = FixtureComponent
  
  val name = "fixture"
  val template = """
    <v-layout column v-if="fx">
      <v-layout row>
        <v-btn style="top:-14px;" icon v-if="fx.result" v-on:click="showResult = !showResult"><v-icon>mdi-check</v-icon></v-btn>
        <v-btn style="top:-14px;" icon v-if="!fx.result" v-on:click="addResult()"><v-icon>mdi-plus</v-icon></v-btn>
        <span >{{async(fx.home).name}} - {{async(fx.away).name}} @ {{async(fx.venue).name}}</span>
      </v-layout>
      <v-layout row v-if="showResult && fx.result">
        <span style="position:relative;top:28px;"><h4>Result :&nbsp;</h4></span>
        <v-text-field label="Home Score" v-model.number="fx.result.homeScore" type="number"></v-text-field>
        <v-text-field label="Away Score" v-model.number="fx.result.awayScore" type="number"></v-text-field>
      </v-layout>
      <v-layout row v-if="showResult && fx.result.reports">
        <span style="position:relative;top:14px;"><h4>Reports :&nbsp;</h4></span><v-btn text v-on:click="editText(report.text.id)" v-for="report in async(fx.result.reports).reports" :key="report.text.id">{{async(report.team).shortName}}...</v-btn>
      </v-layout>
      <v-divider></v-divider>
      <span>&nbsp</span>
    </v-layout>
"""

  
  def editText(c:facade, textId:String) = {
    c.$router.push(s"/maintain/text/$textId")
  }
  
  def addResult(c:facade) = {
    c.fx = FixtureService.addResult(c.fx)
    FixtureService.save(c.fx)
    c.showResult = true
  }
  
  data("showResult", false)
  data("fx")(c => {c.teamManager.take(c.fixture.home);c.teamManager.take(c.fixture.away);c.fixture})

  props("fixture","fixtures","teamManager")

  
  //subscription("fx")(c => c.fixture.obs.map(f => FixtureService.cache(f)).map(x => {c.teamManager.take(x.home);c.teamManager.take(x.away);x}))
  

  method("editText")({editText _ }:js.ThisFunction)
  method("addResult")({addResult _ }:js.ThisFunction)

}
    