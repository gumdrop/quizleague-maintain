package quizleague.web.site.fixtures

import scala.scalajs.js

import quizleague.web.model.Fixture
import quizleague.web.util.rx._

import quizleague.web.core._
import rxscalajs.Observable
import quizleague.web.core.IdComponent
import com.felstar.scalajs.vue.VueComponent
import com.felstar.scalajs.vue._
import quizleague.web.site.results.TableUtils
import rxscalajs.Observable
import quizleague.web.core.DialogComponentConfig


@js.native
trait SimpleFixturesComponent extends VueRxComponent {
  def fixtures: Observable[js.Array[Fixture]] = js.native
}

object SimpleFixturesComponent extends Component {

  type facade = SimpleFixturesComponent

  val name = "ql-fixtures-simple"

  val template = """
   <div v-if="list" class="ql-fixtures-simple">
      <table>
        <ql-fixture-line v-for="fixture in list" :key="fixture.id" :fixture="fixture" :inlineDetails="inlineDetails"></ql-fixture-line>
      </table>
   </div>

"""

  prop("fixtures")
  //prop("list")
  prop("inlineDetails")
  subscription("list","fixtures")(_.fixtures)
  components(FixtureLineComponent)


}


object FixtureLineComponent extends Component with TableUtils with DialogComponentConfig{
  type facade = VueRxComponent with VuetifyComponent with DialogComponent
  val name = "ql-fixture-line"
  val template = """
      <tr>
        <td v-if="inlineDetails" class="inline-details" ><span v-if="!short">{{fixture.date| date("d MMM yyyy")}}</span><span v-else>{{fixture.date| date("d-MM-yy")}}</span> : {{fixture.parentDescription}} {{fixture.description}}</td>
        <td v-if="!fixture.result" class="home"><ql-team-name :team="fixture.home" :short="short"></ql-team-name></td><td v-else class="home" :class="nameClass(fixture.result.homeScore, fixture.result.awayScore)"><ql-team-name :short="short" :team="fixture.home"></ql-team-name></td>
        <td v-if="!fixture.result"></td><td v-else class="score">{{fixture.result.homeScore}}</td>
        <td> - </td>
        <td v-if="!fixture.result"></td><td v-else class="score">{{fixture.result.awayScore}}</td>
        <td v-if="!fixture.result" class="away"><ql-team-name :team="fixture.away" :short="short"></ql-team-name></td><td v-else class="away" :class="nameClass(fixture.result.awayScore, fixture.result.homeScore)"><ql-team-name :short="short" :team="fixture.away"></ql-team-name></td> 
        <td v-if="!fixture.result"></td>
        <td v-else><div>
          <v-tooltip top>
            <v-btn icon @click.stop="showReports=true" v-if="fixture.result.reports" slot="activator">
              <v-icon style="transform:scale(0.75)">description</v-icon>
            </v-btn>
            <span>Match Reports</span></v-tooltip></div>
          <v-dialog v-model="showReports" max-width="60%" v-bind="dialogSize" lazy v-if="fixture.result.reports">
            <v-card>
              <v-card-title>Reports ::&nbsp;
                <ql-team-name :short="short" :team="fixture.home"></ql-team-name>
                &nbsp;{{fixture.result.homeScore}} - {{fixture.result.awayScore}}&nbsp;
                <ql-team-name :short="short" :team="fixture.away"></ql-team-name>
                <v-spacer></v-spacer>
                 <v-tooltip top>
                   <v-btn icon slot="activator" v-on:click="showReports=false">
                     <v-icon>close</v-icon>
                   </v-btn>
                   <span>Close</span>
                 </v-tooltip>
               </v-card-title>
              <ql-reports :id="fixture.result.reports.id"></ql-reports>
            </v-card>
         </v-dialog>
        </td> 
      </tr>"""
  components(ReportsComponent)
  data("showReports", false)
  data("short")(c => c.$vuetify.breakpoint.smAndDown)
  prop("fixture")
  prop("inlineDetails")
  method("nameClass")(nameClass _ )
}

object ReportsComponent extends Component{
  type facade = IdComponent
  val name = "ql-reports"
  val template = """
    <v-container grid-list-sm v-if="reports">
      <v-layout column>
      <v-flex v-for="report in reports.reports" :key="report.id">
        <v-card >
        <v-card-title><h5>{{async(report.team).name}}</h5></v-card-title>
        <v-card-text v-if="report.text">
          <ql-text :id="report.text.id"></ql-text>
        </v-card-text>
      </v-card> 
      </v-flex>
    </v-layout>
    </v-container>"""
  
  prop("id")
  subscription("reports", "id")(c => ReportsService.get(c.id))
}
