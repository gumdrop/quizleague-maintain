package quizleague.web.site.venue

import quizleague.web.core.RouteComponent
import scalajs.js
import scala.scalajs.js.JSConverters._
import js.Dynamic.literal
import quizleague.web.service.venue._
import com.felstar.scalajs.vue.Vue
import quizleague.web.core._
import rxscalajs.subjects.ReplaySubject
import quizleague.web.model.Venue
import rxscalajs.Observable
import scala.scalajs.js.annotation.ScalaJSDefined
import rxscalajs.Observable
import rxscalajs.facade.ObservableFacade
import scala.scalajs.js.ThisFunction0
import quizleague.web.core.Component
import quizleague.web.core.IdComponent
import quizleague.web.core.GridSizeComponentConfig



object VenuePage extends RouteComponent {
  override val template = """<ql-venue :id="$route.params.id"></ql-venue>"""
}

object VenueComponent extends Component with GridSizeComponentConfig{

  override type facade = IdComponent
  
  override val name = "ql-venue"
  override val template = """
          <v-container v-bind="gridSize" fluid v-if="venue">
          <v-layout column>
           <v-flex>
             <v-card>
                <v-card-text>
                  <v-container grid-list-sm fluid>
                    <v-layout row wrap justify-end>
                     <v-layout column>
                      <v-flex>
                        <div>Address : </div>
                        <div class="pl-2" v-html="lineBreaks(venue.address)"></div>
                        <div class="hidden-xs-only">
                          <p></p>
                          <iframe :src="embeddedUrl(venue)" width="400" height="300" frameborder="0" style="border: 0" ></iframe>
                        </div>
                        <div class="hidden-sm-and-up"><a :href="linkUrl(venue)" target="_blank">map</a></div>
                       </v-flex>
                       <v-flex>
                         <div>email : <a :href="'mailto:' + venue.email">{{venue.email}}</a></div>
                         <div>website : <a :href="venue.website" target="_blank">{{venue.website}}</a></div>
                         <div>phone : {{venue.phone}}</div>
                      </v-flex>
                     </v-layout>
                     <v-flex v-if="venue.imageURL" class="hidden-xs-only text-xs-left text-sm-left text-md-right text-lg-right text-xl-right" >
                        <img :src="venue.imageURL">
                     </v-flex>
                   </v-layout>
                  </v-container>
                </v-card-text>
              </v-card>
            </v-flex>
            </v-layout>
          </v-container>"""
  
  def embeddedUrl(venue:Venue) = makeParts(venue).join("")
  
  def linkUrl(venue:Venue) = makeParts(venue).take(2).join("")
    
  private def makeParts(venue:Venue) = {
    js.Array("https://maps.google.com/maps?&q=",js.URIUtils.encodeURIComponent(s"${venue.name} ${venue.address}".replaceAll("\\s", "+")), "&output=embed")
  } 
  props("id")
  subscription("venue","id")(v => VenueService.get(v.id))
  method("lineBreaks")((s: String) => s.replaceAll("\\n", "<br>"))
  method("embeddedUrl")(embeddedUrl _)
  method("linkUrl")(linkUrl _)


}

object VenueTitleComponent extends RouteComponent {
  override val template = """<ql-venue-title :id="$route.params.id"></ql-venue-title>"""
}

object VenueTitle extends Component {
  
  type facade = IdComponent
  
  val name = "ql-venue-title"
  val template = """
    <v-toolbar      
      color="orange darken-3"
      dark
      clipped-left
      v-if="venue">
      <v-toolbar-title class="white--text" >
        {{venue.name}}
      </v-toolbar-title>
    </v-toolbar>"""
  

  
   props("id")
   subscription("venue","id")(v => VenueService.get(v.id))

}

object VenueMenuComponent extends Component {

  override val name = ""  
  
  override val template = """<v-list dense v-if="venues">
     <v-list-group no-action :value="true">
            <v-list-tile slot="item" @click="">
              <v-list-tile-action>
                <v-icon>location_on</v-icon>
              </v-list-tile-action>
              <v-list-tile-content>
                <v-list-tile-title>Venues</v-list-tile-title>
              </v-list-tile-content>
              <v-list-tile-action>
                <v-icon>keyboard_arrow_down</v-icon>
              </v-list-tile-action>
            </v-list-tile>
                    <v-list-tile v-for="venue in sort(venues) " :key="venue.id">
                    <v-btn :to="'/venue/' + venue.id" flat style="text-transform: none;">{{venue.name}}</v-btn>
                    </v-list-tile>
          </v-list-group>
                   </v-list>"""
    subscription("venues")(v => VenueService.list)
    method("sort")((venues:js.Array[Venue]) => venues.sortBy(_.name))
}