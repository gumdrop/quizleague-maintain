package quizleague.web.maintain.team

import quizleague.web.core._
import quizleague.web.maintain.component.TemplateElements._
import com.felstar.scalajs.vue.VueRxComponent
import quizleague.web.maintain.component.ItemComponentConfig
import quizleague.web.model._
import quizleague.web.maintain.venue._
import quizleague.web.maintain.user.UserService
import quizleague.web.util.component.SelectUtils

object TeamComponent extends ItemComponentConfig[Team] with RouteComponent {

  val service = TeamService
  def parentKey(c:facade) = null
  val venueService = VenueService
  def venues() = SelectUtils.model[Venue](venueService)(_.name)
  def users() = SelectUtils.model[User](UserService)(_.name)

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
        <v-text-field
          label="Short Name"
          v-model="item.shortName"
          :rules=${valRequired("Short Name")}
          required
        ></v-text-field>
        <v-select
          label="Venue"
          :items="venues"
          v-model="item.venue"
          >
        </v-select>
        <v-select
          label="Users"
          :items="users"
          v-model="item.users"
          multiple
          chips
          >
        </v-select>
        <div><v-btn v-on:click ="editText(item.text.id)" text><v-icon>mdi-card-text-outline</v-icon>Text</v-btn></div>
        $chbxRetired 
     </v-layout>
     $formButtons
    </v-form>
  </v-container>"""

 subscription("venues"){c:facade => venues()}
 subscription("users"){c:facade => users()}
}
