package quizleague.web.site.calendar

import java.time._

import scala.scalajs.js
import quizleague.web.core._
import quizleague.web.site._
import quizleague.web.site.season.SeasonIdComponent
import com.felstar.scalajs.vue._
import quizleague.web.site.season.SeasonFormatComponent

import js.JSConverters._

object CalendarPage extends RouteComponent with NoSideMenu{
  
  val template = """<ql-calendar v-if="season" :seasonId="season.id"></ql-calendar>"""
  components(CalendarComponent)
  subscription("season")(c => CalendarViewService.season)
}

@js.native
trait CalendarComponent extends SeasonIdComponent with VuetifyComponent{
  var items:js.Array[DateWrapper]
  var now:String
}

object CalendarComponent extends Component with GridSizeComponentConfig{
  type facade = CalendarComponent
  val name = "ql-calendar" 
  val template = """
  <v-container v-bind="gridSize"  v-if="items" class="ql-calendar" fluid >
    <v-timeline v-bind="dense" v-if="viewType=='timeline'">
      <v-timeline-item v-for="item in items" :key="item.date"  :color="colour(item.events)" :icon="icon(item.events)" fill-dot>
        <v-card>
           <v-card-title :class="colour(item.events)"><h5 class="display-1 white--text font-weight-light">{{item.date | date("EEEE d MMMM yyyy")}}</h5></v-card-title>
           <v-card-text>
            <div v-for="event in item.events">
                <ql-fixtures-event v-if="event.eventType === 'fixtures'" :event="event" :panelVisible="false"></ql-fixtures-event>
                <ql-calendar-event v-if="event.eventType === 'calendar'" :event="event"></ql-calendar-event>
                <ql-competition-event v-if="event.eventType === 'competition'" :event="event"></ql-competition-event>
            </div>
           </v-card-text>
        </v-card>
      </v-timeline-item>
    </v-timeline>
    <v-layout column v-if="viewType=='calendar'">
    <style lang="stylus" scoped>

 </style>
    <v-flex>{{now}}
    <v-calendar v-if="items"
          ref="calendar"
          v-model="now"
          type="month"
          color="primary"

          >
      <template v-slot:day="{ date }" >
        <template v-for="(event,i) in (dateMap[date]? dateMap[date].events : [])">
           <v-menu
                 :key="i"
                 v-model="event.open"
                 full-width
                 offset-x

               >
                 <template v-slot:activator="{ on }">
                   <div
                     v-ripple
                     :class="'my-event ' + colour([event])"
                     v-on="on"
                   ><v-icon class="white--text">{{icon([event])}}</v-icon>
                <span v-if="event.eventType === 'fixtures'">{{event.fixtures.parentDescription}} : {{event.fixtures.description}}</span>
                <span v-if="event.eventType === 'calendar'">{{event.event.description}}</span>
                <span v-if="event.eventType === 'competition'">{{event.competition.name}}</span>
            </div>
                 </template>
                 <v-card
                   color="grey lighten-4"
                   min-width="350px"
                   flat
                 >
                   <v-toolbar
                     :color="colour([event])"
                     dark
                   >
                    <v-icon>{{icon([event])}}</v-icon>
                     <v-toolbar-title>{{event.date | date("EEEE d MMMM yyyy")}}</v-toolbar-title>
                     <v-spacer></v-spacer>
                   </v-toolbar>
                   <v-card-title primary-title>
                     <span ></span>
                   </v-card-title>
                   <v-card-text>
                    <ql-fixtures-event v-if="event.eventType === 'fixtures'" :event="event" :panelVisible="true"></ql-fixtures-event>
                    <ql-calendar-event v-if="event.eventType === 'calendar'" :event="event"></ql-calendar-event>
                    <ql-competition-event v-if="event.eventType === 'competition'" :event="event"></ql-competition-event>
                   </v-card-text>
                 </v-card>
               </v-menu>
                </template>
            </template>
     </v-calendar>
     </v-flex>
      <v-layout row>
      <v-flex
      sm4
      xs12
      class="text-sm-left text-xs-center"
    >
      <v-btn @click="$refs.calendar.prev()">
        <v-icon
          dark
          left
        >
          keyboard_arrow_left
        </v-icon>
        Prev
      </v-btn>
    </v-flex>
    <v-spacer></v-spacer>
    <v-flex
      sm4
      xs12
      class="text-sm-right text-xs-center"
    >
      <v-btn @click="$refs.calendar.next()">
        Next
        <v-icon
          right
          dark
        >
          keyboard_arrow_right
        </v-icon>
      </v-btn>
    </v-flex>
    </v-layout>
    </v-layout>
  </v-container>"""

  def icon(events:js.Array[EventWrapper]) = events.headOption.fold("mdi-calendar")(e => e match {
    case c: CalendarEventWrapper => "mdi-calendar"
    case f: FixturesEventWrapper => f.competition.icon
    case s: CompetitionEventWrapper => s.competition.icon
  })

  def colour(events:js.Array[EventWrapper]) = events.headOption.fold("gray")(e => (e match {
    case c: CalendarEventWrapper => "blue"
    case f: FixturesEventWrapper => f.competition.typeName match{case "league" => "green" case "cup" => "red"}
    case s: CompetitionEventWrapper => "purple"
  }) + " darken-3")




  props("seasonId","viewType")
  data("type", "month")
  data("typeOptions", js.Array("day","week", "month"))
  data("start", null)
  data("now", LocalDate.now.toString)
  subscription("items", "seasonId")(c => CalendarViewService.events(c.seasonId))
  subscription( "dateMap", "seasonId")(c => CalendarViewService.events(c.seasonId).map(x => {c.now = (x.headOption.fold(LocalDate.now)(d => LocalDate.parse(d.date)).toString);x.map( y => (y.date, y)).toMap.toJSDictionary}))
  subscription("viewType")(c => CalendarViewService.viewType)
  components(FixturesEventComponent,CalendarEventComponent,CompetitionEventComponent)
  method("colour"){colour _}
  method("icon"){icon _}
  method("itemMap")({c:facade => c.items.map(y => (y.date,y)).toMap.toJSDictionary}:js.ThisFunction)
  def dense(c:facade) = js.Dictionary("dense" -> c.$vuetify.breakpoint.xsOnly)
  computed("dense")({dense _}:js.ThisFunction)
  
}

object CalendarTitleComponent extends RouteComponent with SeasonFormatComponent{
  val template = """
    <v-toolbar      
      color="yellow darken-3"
      dark
      clipped-left
      >
      <ql-title>Calendar {{formatSeason(s)}}</ql-title>
      <v-toolbar-title class="white--text" >
        Calendar
      </v-toolbar-title>
      &nbsp;<h3><ql-season-select :season="season"></ql-season-select></h3> &nbsp;<v-btn-toggle v-model="viewType" ><v-btn flat value="timeline" color="yellow darken-3">Timeline</v-btn><v-btn flat value="calendar" color="yellow darken-3">Calendar</v-btn></v-btn-toggle>
    </v-toolbar>"""
  
  data("season", CalendarViewService.season)
  data("viewType","timeline")
  subscription("s")(c => CalendarViewService.season)
  watch("viewType")((c,v) => CalendarViewService.viewType.next(v.toString))
}


@js.native
trait EventComponent extends VueRxComponent{
  
  val event:EventWrapper
}
  

@js.native
trait PanelComponent extends EventComponent{

  var panelVisible:Boolean

}


trait EventComponentConfig extends Component{
   
   type facade = PanelComponent
   data("panelVisible",false)
   props("event")
   method("togglePanel")({c:facade => c.panelVisible = !c.panelVisible}:js.ThisFunction)
}



object FixturesEventComponent extends EventComponentConfig{
  
  val name = "ql-fixtures-event"
   val template = s"""
      <v-layout column align-start class="panel-component">
          <v-flex align-start><b><router-link :to="'/competition/' + event.competition.id + '/' + event.competition.typeName"><v-icon>{{event.competition.icon}}</v-icon>&nbsp;{{event.fixtures.parentDescription}} {{event.fixtures.description}}</router-link></b>
            <v-btn icon v-on:click="togglePanel" class="#view-btn">
             <v-icon v-if="!panelVisible">visibility</v-icon>
             <v-icon v-if="panelVisible">visibility_off</v-icon>
            </v-btn>
          </v-flex> 
          <v-flex><v-slide-y-transition><ql-fixtures-simple v-if="panelVisible" :fixtures="event.fixtures.fixtures | combine"></ql-fixtures-simple></v-slide-y-transition></v-flex>

     </v-layout>
"""

  prop("panelVisible")

}

object CalendarEventComponent extends EventComponentConfig{
  
  val name = "ql-calendar-event"
  val template = """
    <v-layout column align-start>
      <v-flex><b>{{event.event.description}}</b></v-flex>
      <v-flex>Time : {{event.event.time}}</v-flex>
      <v-flex v-if="event.event.venue">Venue : <router-link router-link :to="'/venue/' + event.event.venue.id">{{async(event.event.venue).name}}</router-link></v-flex>
     </v-layout>
      """

}

object CompetitionEventComponent extends EventComponentConfig{
  
  val name = "ql-competition-event"
  val template =
    """
      <v-layout column align-start >
        <v-flex><b><router-link :to="'/competition/' + event.competition.id+'/'+event.competition.typeName"><v-icon>{{event.competition.icon}}</v-icon>&nbsp;{{event.competition.name}}</router-link></b></v-flex>
        <v-flex>Time : {{event.event.time}}</v-flex>
        <v-flex>Venue : <router-link v-if="event.event.venue"router-link :to="'/venue/' + event.event.venue.id">{{async(event.event.venue).name}}</router-link></v-flex>
       </v-layout>"""

}

