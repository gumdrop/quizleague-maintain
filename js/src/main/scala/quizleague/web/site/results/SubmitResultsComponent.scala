package quizleague.web.site.results

import quizleague.web.core._
import quizleague.web.site.user.UserService
import quizleague.web.site.fixtures.FixtureService
import scalajs.js
import js.JSConverters._
import quizleague.web.site._
import quizleague.web.model._
import quizleague.web.util.validation.Functions
import quizleague.web.core.DialogComponentConfig
import quizleague.web.core.DialogComponent

@js.native
trait SubmitResultsComponent extends com.felstar.scalajs.vue.VueRxComponent with DialogComponent{
  var email:String
  var fixtures:js.Array[Fixture]
  val appData:ApplicationContext
  var hasResults:Boolean
  val reportText:String
  var confirm:Boolean
}
object SubmitResultsComponent extends RouteComponent with DialogComponentConfig{
  
  type facade = SubmitResultsComponent 
  
  val emailRegex = """^[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,}$"""
  
  val template ="""
    <v-container>
      <v-form v-model="valid">
      <v-layout column v-if="appData">
       <v-text-field v-model="email" label="Enter your mail address" prepend-icon="email"></v-text-field>
      <div v-if="hasResults">This result has already been submitted, but you can add a match report.</div>
      <p></p>
      <v-flex v-for="fixture in fixtures" v-if="!hasResults">
          {{fixture.description}} - {{fixture.date | date("dd MMM yyyy")}}
          <v-text-field v-model.number="fixture.result.homeScore"  :rules="[required('Home Score')]" :label="async(fixture.home).name" type="number" ></v-text-field>
          <v-text-field v-model.number="fixture.result.awayScore"  :rules="[required('Away Score')]" :label="async(fixture.away).name" type="number" ></v-text-field>
      </v-flex>
      <v-flex v-else>
        <ql-fixtures-simple :fixtures="fixtures | wrap" :inlineDetails="true"></ql-fixtures-simple>
      </v-flex>
      <v-flex v-if="fixtures.length > 0">
        <v-text-field v-model="reportText" textarea auto-grow label="Match Report"></v-text-field>
        <div><v-btn v-on:click="preSubmit" flat color="primary" :disabled="!valid">Submit<v-icon right>send</v-icon></v-btn></div>
      </v-flex>
     <v-dialog v-model="confirm" persistent lazy max-width="60%" v-bind="dialogSize" >
        <v-card>
          <v-card-title>Check Results</v-card-title>
          <v-card-text>
            <ql-fixtures-simple :fixtures="fixtures | wrap" :inlineDetails="true"></ql-fixtures-simple>
          </v-card-text>
          </v-card-actions><v-btn flat v-on:click="cancel"><v-icon left>cancel</v-icon>Cancel</v-btn><v-btn flat color="primary" v-on:click="submit"><v-icon left>check</v-icon>Ok</v-btn></v-card-actions>
        </v-card>
     </v-dialog>

      </v-layout>
    </v-form>
    </v-container>"""
  
  def handleEmail(c:facade) = {
    if(c.email.toLowerCase.matches(emailRegex)) {
      FixtureService.fixturesForResultSubmission(c.email, c.appData.currentSeason.id).subscribe(handleFixtures(c) _)
    }
  }
  
  def handleFixtures(c:facade)(fixtures:js.Array[Fixture]) = {
    c.hasResults = fixtures.exists(_.result != null)
    c.fixtures = if(c.hasResults) fixtures else fixtures.map(Fixture.addBlankResult _)
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
    FixtureService.submitResult(c.fixtures, c.reportText, c.email)
    c.email = ""
    c.fixtures = js.Array()
    c.hasResults = false
  }
  
  def mounted(c:facade) = c.$subscribeTo(c.$watchAsObservable("email").debounceTime(100),(x:js.Any) => handleEmail(c))
  
  
  subscription("appData")(c => ApplicationContextService.get)
  method("submit")({submit _}:js.ThisFunction)
  method("preSubmit")({preSubmit _}:js.ThisFunction)
  method("cancel")({cancel _}:js.ThisFunction)
  method("required")(Functions.required _)
  data("email","")
  data("hasResults",false)
  data("fixtures", js.Array())
  data("reportText",null)
  data("confirm", false)
  data("valid", false)
  
  override val mounted = {(c:facade) => mounted(c)} :js.ThisFunction
}