package quizleague.web.maintain.user

import quizleague.web.core._
import quizleague.web.maintain.component.TemplateElements._
import com.felstar.scalajs.vue.VueRxComponent
import quizleague.web.maintain.component.ItemComponentConfig
import quizleague.web.model._

object UserComponent extends ItemComponentConfig[User] with RouteComponent {

  val service = UserService

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
        <v-text-field
          label="Email"
          v-model="item.email"
          type="email"
          :rules=${valRequired("Email")}
          required
        ></v-text-field>
        $chbxRetired 
     </v-layout>
     $formButtons
    </v-form>
  </v-container>"""

}
