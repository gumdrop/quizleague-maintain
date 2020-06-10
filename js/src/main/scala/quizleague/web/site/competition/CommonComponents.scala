package quizleague.web.site.competition

import quizleague.web.core.{@@, Component, GridSizeComponentConfig, IdComponent, KeyComponent, RouteComponent}
import KeyComponent._
import quizleague.web.model.Key
import com.felstar.scalajs.vue.VuetifyComponent
import com.felstar.scalajs.vue.VueComponent
import quizleague.web.core.GridSizeComponentConfig
import quizleague.web.site.season.{SeasonFormatComponent, SeasonService}

object LeagueTables extends Component{
  type facade = KeyComponent
  val name = "league-tables"
  val template = """
    <v-card class="mb-x">
      <v-card-title primary-title><h3 class="headline mb-0">League Table</h3></v-card-title>
      <v-card-text>
        <ql-league-table v-for="table in tables" :key="table.id" :keyval="table.key" class="mb-3"></ql-league-table>
      </v-card-text>
    </v-card>"""
  
  props ("keyval")
  subscription ("tables","keyval")(c => CompetitionService.get(key(c)).flatMap(_.leaguetable))
}

object LatestResults extends Component{
  type facade = KeyComponent
  val name = "latest-results"
  val template = """
    <v-card class="mb-0">
      <v-card-title primary-title><h3 class="headline mb-0">Results</h3></v-card-title>
      <v-card-title><span class="mb-0">Latest results</span></v-card-title>
      <v-card-text  v-if="latestResults">
        <v-container grid-list-sm v-for="results in latestResults" :key="results.id">
          <v-layout column>
            <v-flex><h3 class="headline mb-0">{{results.date | date("d MMMM yyyy")}} : {{results.description}}</h3></v-flex>
            <v-flex><ql-fixtures-simple :fixtures="results.fixture" ></ql-fixtures-simple></v-flex>
          </v-layout>
        </v-container>
      </v-card-text>
      <v-card-actions>
        <v-btn color="primary" text to="results">Show All</v-btn>
      </v-card-actions>
    </v-card>"""
  
  props("keyval")
  
  subscription ("latestResults","keyval")(c => CompetitionViewService.latestResults(key(c),1))
    
}

object NextFixtures extends Component{
  type facade = KeyComponent
  val name = "next-fixtures"
  val template = """<v-card class="mb-0">
      <v-card-title primary-title><h3 class="headline mb-0">Fixtures</h3></v-card-title>
      <v-card-title><span class="mb-0">Next fixtures</span></v-card-title>
      <v-card-text  v-if="nextFixtures">
        <v-container grid-list-sm fluid v-for="fixtures in nextFixtures" :key="fixtures.id">
          <v-layout column>
            <v-flex><h3 class="headline mb-0">{{fixtures.date | date("d MMMM yyyy")}} : {{fixtures.description}}</h3></v-flex>
            <v-flex><ql-fixtures-simple :fixtures="fixtures.fixture" ></ql-fixtures-simple></v-flex>
          </v-layout>
        </v-container>
      </v-card-text>
      <v-card-actions>
        <v-btn color="primary" text to="fixtures">Show All</v-btn>
      </v-card-actions>
    </v-card>"""
  

 
  props("keyval")
  subscription("nextFixtures","keyval")(c => CompetitionViewService.nextFixtures(key(c),1))
}


object CompetitionTitle extends RouteComponent{
  val template = """<competition-title :keyval="decode($route.params.key)" :text="null"></competition-title>"""
  components(CompetitionTitleComponent)
}

object CompetitionResultsTitle extends RouteComponent{
  val template = """<competition-title :keyval="decode($route.params.key)" text="Results"></competition-title>"""
  components(CompetitionTitleComponent)
}

object CompetitionFixturesTitle extends RouteComponent{
  val template = """<competition-title :keyval="decode($route.params.key)" text="Fixtures"></competition-title>"""
  components(CompetitionTitleComponent)
}
  
object CompetitionTitleComponent extends Component with SeasonFormatComponent{
  type facade = KeyComponent
  val name = "competition-title"
  val template = """<v-toolbar      
      color="purple lighten-3"
      class="subtitle-background"
      dense
      >
      <v-toolbar-title  v-if="item && season" >
       <ql-title>{{item.name}} {{formatText(text)}}{{formatSeason(season)}}</ql-title>
       <v-icon v-if="item.icon" style="position:relative;top:-2px">{{item.icon}}</v-icon>&nbsp;&nbsp;<span>{{item.name}} {{formatText(text)}}{{season.startYear}}/{{season.endYear}}</span>
      </v-toolbar-title>
      <v-spacer></v-spacer>
      <v-toolbar-items>
         <v-btn href="#" v-scroll-to="'#chat'" text ><v-icon left>mdi-chat</v-icon><span>Chat</span></v-btn>
      </v-toolbar-items>
    </v-toolbar>"""
  
  props("keyval","text")
  subscription("item","keyval")(c => CompetitionService.get(key(c)))
  subscription("season")(c => CompetitionViewService.parentSeason(key(c)))
  method("formatText")((text:String) => if(text == null || text.isEmpty) "" else s": $text ")
}



object ResultsPage extends RouteComponent{
  val template = """<all-results :keyval="decode($route.params.key)"></all-results>"""
  components(AllResults) 
}

trait ResultsComponent extends Component {
  type facade = KeyComponent
 

  props("keyval")

  subscription("latestResults","keyval")(c => CompetitionViewService.latestResults(key(c), take))

  def take:Int
}


object AllResults extends ResultsComponent with GridSizeComponentConfig{
  val name = "all-results"
  val template = """
    <v-container v-bind="gridSize" fluid v-if="latestResults">
    <v-layout column>
    <v-flex v-for="results in latestResults" :key="results.id">
      <v-card>
        <v-card-title primary-title><h3 class="headline mb-0">{{results.date | date("d MMMM yyyy")}} : {{results.description}}</h3></v-card-title>
        <v-card-text>
            <ql-fixtures-simple :fixtures="results.fixture" ></ql-fixtures-simple>
          </div>
        </v-card-text>
      </v-card>
    </v-flex>
    </v-layout>
    </v-container>"""
    
    val take = Integer.MAX_VALUE
}

object FixturesPage extends RouteComponent{
  val template = """<remaining-fixtures :id="$route.params.id"></remaining-fixtures>"""
  components(RemainingFixtures) 
}

object RemainingFixtures extends Component with GridSizeComponentConfig{
  type facade = KeyComponent
  val name = "remaining-fixtures"
  val template = """
    <v-container v-bind="gridSize" fluid v-if="nextFixtures">
      <v-layout column>
        <v-flex v-for="fixtures in nextFixtures" :key="fixtures.id">
          <v-card>
            <v-card-title primary-title><h3 class="headline mb-0">{{fixtures.date | date("d MMMM yyyy")}} : {{fixtures.description}}</h3></v-card-title>
            <v-card-text>
                <ql-fixtures-simple :fixtures="fixtures.fixture" ></ql-fixtures-simple>
            </v-card-text>
          </v-card>
        </v-flex>
      </v-layout>
    </v-container>"""
  

 
  props("keyval")

  subscription("nextFixtures","keyval")(c => CompetitionViewService.nextFixtures(key(c)))

  
}

