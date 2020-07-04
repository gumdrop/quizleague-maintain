package quizleague.web.site.results

import quizleague.web.core._
import quizleague.web.site.fixtures.FixtureService

import scalajs.js
import quizleague.web.site._
import quizleague.web.model._
import quizleague.web.util.validation.Functions
import quizleague.web.core.DialogComponentConfig
import quizleague.web.core.DialogComponent
import quizleague.web.site.login.{LoggedInUser, LoginService}
import quizleague.web.util.Logging._

@js.native
trait SubmitResultsComponent extends com.felstar.scalajs.vue.VueRxComponent with DialogComponent{
  var user:LoggedInUser
  var fixtures:js.Array[Fixture]
  val appData:ApplicationContext
  var hasResults:Boolean
  var reportText:String
  var confirm:Boolean
  var showProgress:Boolean
}
object SubmitResultsComponent extends RouteComponent with DialogComponentConfig{
  
  type facade = SubmitResultsComponent 

  val template ="""
    <v-container>
    <ql-text-box>
      <v-form v-model="valid">
      <v-layout column>
      <div v-if="hasResults">This result has been submitted, but you can still add your match report.</div>
      <p></p>
      <v-flex align-center style="padding-left:48%;"><v-progress-circular v-if="showProgress" indeterminate color="primary"></v-progress-circular></v-flex>
      <v-flex v-for="fixture in fixtures" v-if="!hasResults">
          {{async(async(fixture.parent).parent).name}} {{async(fixture.parent).description}} - {{async(fixture.parent).date | date("dd MMM yyyy")}}
          <v-text-field v-model.number="fixture.result.homeScore"  :rules="[required('Home Score')]" :label="async(fixture.home).name" type="number" ></v-text-field>
          <v-text-field v-model.number="fixture.result.awayScore"  :rules="[required('Away Score')]" :label="async(fixture.away).name" type="number" ></v-text-field>
      </v-flex>
      <v-flex v-if="hasResults">
        <ql-fixtures-simple :fixtures="fixtures | wrap" :inlineDetails="true"></ql-fixtures-simple>
      </v-flex>
      <v-flex v-if="fixtures.length > 0">
        <v-textarea v-model="reportText" outline auto-grow label="Match Report" >
          <template slot="append"><v-tooltip top><template v-slot:activator="{ on }"><v-btn v-on="on" text fab small color="light-blue" @click="preview=!preview"><v-icon>mdi-eye-outline</v-icon></v-btn></template><span>Preview</span></v-tooltip></template>
        </v-textarea>
        <div><v-btn v-on:click="preSubmit" text color="primary" :disabled="!valid">Submit<v-icon right>mdi-send</v-icon></v-btn></div>
        <transition name="fade">
          <v-card v-if="preview" >
            <v-card-title><v-icon color="light-blue">mdi-eye-outline</v-icon><div class="light-blue--text pl-1" >Preview</div></v-card-title>
            <v-card-text>
              <ql-markdown :text="reportText ? reportText : ''"></ql-markdown>
            </v-card-text>
          </v-card>
        </transition>
      </v-flex>
     <v-dialog v-model="confirm" persistent max-width="60%" v-bind="dialogSize" >
        <v-card>
          <v-card-title>Check Results</v-card-title>
          <v-card-text>
            <ql-fixtures-simple :fixtures="fixtures | wrap" :inlineDetails="true"></ql-fixtures-simple>
          </v-card-text>
          </v-card-actions><v-btn text v-on:click="cancel"><v-icon left>mdi-cancel</v-icon>Cancel</v-btn><v-btn text color="primary" v-on:click="submit"><v-icon left>mdi-check</v-icon>Ok</v-btn></v-card-actions>
        </v-card>
     </v-dialog>

      </v-layout>
    </v-form>
    </ql-text-box>
    </v-container>"""
  
  def getFixtures(c:facade, user:LoggedInUser) = {
    c.showProgress = true
    FixtureService.fixturesForResultSubmission(user.team.id).subscribe(handleFixtures(c) _)
  }
  
  def handleFixtures(c:facade)(fixtures:js.Array[Fixture]) = {
    c.hasResults = fixtures.exists(_.result != null)
    c.fixtures = if(c.hasResults) fixtures else fixtures.map(Fixture.addBlankResult _)
    c.showProgress = false
  }
  
  def preSubmit(c:facade) {
    if(c.hasResults){
      submit(c)
    }
    else{
       c.confirm = true
    }

  }
  
  def cancel(c:facade) {
    c.confirm = false
  }
  
  def submit(c:facade){
    c.confirm = false
    FixtureService.submitResult(c.fixtures, c.reportText, c.user.siteUser.user.id)
    c.reportText = ""
    c.fixtures = js.Array()
    c.hasResults = false
  }
  
 def mounted(c:facade) =LoginService.userProfile.filter(_ != null).subscribe(user => getFixtures(c,user))
  
  
  subscription("appData")(c => ApplicationContextService.get)
  subscription("user")(c => LoginService.userProfile)

  method("submit")({submit _}:js.ThisFunction)
  method("preSubmit")({preSubmit _}:js.ThisFunction)
  method("cancel")({cancel _}:js.ThisFunction)
  method("required")(Functions.required _)
  method("getFixtures")({getFixtures _}:js.ThisFunction)
  data("hasResults",false)
  data("fixtures", js.Array())
  data("reportText",null)
  data("confirm", false)
  data("valid", false)
  data("showProgress", false)
  data("preview", false)
  
  override val mounted = {(c:facade) => mounted(c)} :js.ThisFunction
}

object SubmitResultsTitleComponent extends RouteComponent{
  val template = """
    <v-toolbar      
      color="red lighten-3"
      dense
      class="subtitle-background"
      >
      <ql-title>Submit Results</ql-title>
      <v-toolbar-title >
        Submit Results
      </v-toolbar-title>
    </v-toolbar>"""
}

object SubmitResultsInstructionsComponent extends RouteComponent with GridSizeComponentConfig{
  val template = """
  <v-container v-bind="gridSize" fluid>
    <v-layout>
    <v-flex><ql-text-box><ql-named-text name="submit-results-instructions"></ql-named-text></ql-text-box></v-flex>
    </v-layout>
  </v-container>"""
}