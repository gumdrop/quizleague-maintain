package quizleague.web.maintain.globaltext

import quizleague.web.core._
import quizleague.web.maintain.component.TemplateElements._
import com.felstar.scalajs.vue.VueRxComponent
import quizleague.web.maintain.component.ItemComponentConfig
import quizleague.web.model._
import quizleague.web.maintain.venue._
import quizleague.web.maintain.user.UserService
import quizleague.web.util.component.SelectUtils

import scalajs.js
import js.JSConverters._

object GlobalTextComponent extends ItemComponentConfig[GlobalText] with RouteComponent {

  val service = GlobalTextService
  def parentKey(c:facade) = null

  val template = s"""
  <v-container v-if="item">
    <v-form v-model="valid" >
      <v-layout column>
       <v-text-field
          label="Name"
          v-model="item.name"
          :rules=${valRequired("Name")}
          required
        ></v-text-field>
      <v-divider></v-divider>
      <v-layout row v-for="entry in sort(item.text)" :key="entry.text.id">
          <v-text-field
          label="Entry Name"
          v-model="entry.name"
          :rules=${valRequired("Name")}
          required
        ></v-text-field>
        <div><v-btn v-on:click ="editText(entry.text.id)" text><v-icon>mdi-card-text-outline</v-icon>Text</v-btn></div>
      </v-layout>
     </v-layout>
      <v-btn  fixed
              dark
              fab
              bottom
              center
              small
              color="pink"
              v-on:click="add">
          <v-icon>mdi-plus</v-icon>
      </v-btn>
      $formButtons
    </v-form>
  </v-container>"""

  
 def sort(c:facade, entries:js.Array[TextEntry]) = entries.sortBy(_.name)     

 method("sort")({sort _}:js.ThisFunction)
 method("add")({ (c: facade) =>
      {
        val i = service.entryInstance()
        c.item.text.push(i)
      }
    }: js.ThisFunction)
}
