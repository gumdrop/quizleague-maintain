package quizleague.web.maintain.venue

import quizleague.web.core._
import quizleague.web.maintain.component.TemplateElements._
import com.felstar.scalajs.vue.VueRxComponent
import quizleague.web.maintain.component.ItemComponentConfig
import quizleague.web.maintain.fixtures.FixturesComponent.facade
import quizleague.web.model._

object VenueComponent extends ItemComponentConfig[Venue] with RouteComponent {

  val service = VenueService

  def parentKey(c:facade) = null

  val template = s"""
  <v-container v-if="item">
    <v-form v-model="valid" ref="fm">
      <v-layout column>
        <v-text-field
          label="Name"
          v-model="item.name"
          :rules=${valRequired("Name")}
          required
        ></v-text-field>
        <v-textarea
          label="Address"
          v-model="item.address"
          :rules=${valRequired("Address")}
          required
          outline
        ></v-textarea>
        <v-text-field
          label="Phone"
          v-model="item.phone"
          type="phone"
        ></v-text-field>
        <v-text-field
          label="Email"
          v-model="item.email"
          type="email"
        ></v-text-field>
        <v-text-field
          label="Website"
          v-model="item.website"
          type="url"
        ></v-text-field>
        <v-text-field
          label="Image URL"
          v-model="item.imageURL"
          type="url"
        ></v-text-field>
        $chbxRetired 
        <div><img :src="item.imageURL"></div>
     </v-layout>
     $formButtons
    </v-form>
  </v-container>"""

}
