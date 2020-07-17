package quizleague.web.site.calendar

import java.time._

import scala.scalajs.js
import quizleague.web.core._
import quizleague.web.site._
import quizleague.web.site.season.SeasonIdComponent
import com.felstar.scalajs.vue._
import quizleague.web.site.calendar.CalendarComponent.components
import quizleague.web.site.season.SeasonFormatComponent

import js.JSConverters._

object CalendarPage extends RouteComponent with NoSideMenu{
  
  val template = """<ql-calendar v-if="season" :seasonId="season.id"></ql-calendar>"""
  components(CalendarComponent)
  subscription("season")(c => CalendarViewService.season)
}



object CalendarComponent extends Component with GridSizeComponentConfig{
  type facade = SeasonIdComponent with VuetifyComponent
  val name = "ql-calendar" 
  val template = """
  <v-container v-bind="gridSize" class="ql-calendar" fluid >
    <v-scroll-x-transition>
    <v-timeline v-bind="dense" v-if="items && viewType=='timeline'">
      <v-timeline-item v-for="item in items" :key="item.date"  :color="colour(item.events)" :icon="icon(item.events)" fill-dot>
          <v-card>
             <v-card-title :class="colour(item.events)"><h5 class="display-1 white--text font-weight-light">{{item.date | date("EEEE d MMMM yyyy")}}</h5></v-card-title>
             <v-card-text>
              <v-lazy>
                <div v-for="event in item.events">
                    <ql-fixtures-event v-if="event.eventType === 'fixtures'" :event="event" :panelVisible="false"></ql-fixtures-event>
                    <ql-calendar-event v-if="event.eventType === 'calendar'" :event="event"></ql-calendar-event>
                    <ql-competition-event v-if="event.eventType === 'competition'" :event="event"></ql-competition-event>
                </div>
              </v-lazy>
             </v-card-text>
          </v-card>
      </v-timeline-item>
    </v-timeline>
    </v-scroll-x-transition>
    <v-scroll-x-transition>
    <ql-calendar-calendar v-if="items && viewType=='calendar'"></ql-calendar-calendar>
    </v-scroll-x-transition>
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




  props("seasonId")
  subscription("items", "seasonId")(c => CalendarViewService.events(c.seasonId))
  subscription("viewType")(c => CalendarViewService.viewType)
  components(FixturesEventComponent,CalendarEventComponent,CompetitionEventComponent, CalendarCalendarComponent)
  method("colour"){colour _}
  method("icon"){icon _}

  def dense(c:facade) = js.Dictionary("dense" -> c.$vuetify.breakpoint.xsOnly)
  computed("dense")({dense _}:js.ThisFunction)
  
}


object CalendarCalendarComponent extends Component{

  val name = "ql-calendar-calendar"

  val template = """
   <v-layout column >
    <v-flex>
    <ql-text-box>
      <v-calendar v-if="dateMap"
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
                 offset-x

               >
                 <template v-slot:activator="{ on }">
                   <div
                     v-ripple
                     :class="'my-event ' + colour([event])"
                     v-on="on"
                   >
                    <v-icon class="white--text">{{icon([event])}}</v-icon>
                    <span v-if="event.eventType === 'fixtures'">{{async(event.fixtures.parent).name}} : {{event.fixtures.description}}</span>
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
     </ql-text-box>
     </v-flex>
     <v-spacer></v-spacer>
      <v-layout row class="align-content-space-between mt-1" >
      <v-btn @click="$refs.calendar.prev()" fab class="ml-3">
        <v-icon
          dark
          size="64"
        >
          mdi-chevron-left
        </v-icon>
      </v-btn>

    <v-spacer></v-spacer>

      <v-btn @click="$refs.calendar.next()" fab class="mr-3">
         <v-icon
          dark
          size="64"
        >
          mdi-chevron-right
        </v-icon>
      </v-btn>
    </v-layout>
    </v-layout>
  """

  data("now", LocalDate.now.toString)

  subscription( "dateMap")(c => CalendarViewService.allEvents())

  method("colour"){CalendarComponent.colour _}
  method("icon"){CalendarComponent.icon _}

  components(FixturesEventComponent,CalendarEventComponent,CompetitionEventComponent)
}

object CalendarTitleComponent extends RouteComponent with SeasonFormatComponent{
  val template = """
    <v-toolbar      
      color="yellow lighten-3"
      dense
      class="subtitle-background"
      >
      <ql-title>Calendar {{formatSeason(s)}}</ql-title>
      <v-toolbar-title>
        Calendar
      </v-toolbar-title>
      <span style="padding-left:.5em;"></span>
      <v-toolbar-items>
        <ql-season-select :season="season" :inline="true" :disabled="viewType !== 'timeline'"></ql-season-select>
        <v-btn-toggle v-model="viewType" dark>
          <v-btn text value="timeline" color="yellow lighten-3">Timeline</v-btn>
          <v-btn text value="calendar" color="yellow lighten-3">Calendar</v-btn>
        </v-btn-toggle>
      </v-toolbar-items>
     </v-toolbar>"""
  
  data("season", CalendarViewService.season)
  data("viewType", CalendarViewService.getViewType())
  subscription("s")(c => CalendarViewService.season)
  watch("viewType")((c,v) => CalendarViewService.setViewType(v.toString))
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
          <v-flex align-start><b><router-link :to="'/competition/' + event.competition.key.encode + '/' + event.competition.typeName"><v-icon>{{event.competition.icon}}</v-icon>&nbsp;{{async(event.fixtures.parent).name}} {{event.fixtures.description}}</router-link></b>
            <v-btn icon v-on:click="togglePanel" class="#view-btn">
             <v-icon v-if="!panelVisible">mdi-eye</v-icon>
             <v-icon v-if="panelVisible">mdi-eye-off</v-icon>
            </v-btn>
          </v-flex> 
          <v-flex><v-slide-y-transition><ql-fixtures-simple v-if="panelVisible" :fixtures="event.fixtures.fixture"></ql-fixtures-simple></v-slide-y-transition></v-flex>

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

