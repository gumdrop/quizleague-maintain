package quizleague.web.maintain.text

import quizleague.web.core._
import quizleague.web.maintain.component.TemplateElements._
import com.felstar.scalajs.vue.VueRxComponent
import quizleague.web.maintain.component.ItemComponentConfig
import quizleague.web.model._

object TextComponent extends ItemComponentConfig[Text] with RouteComponent {

  val service = TextService

  val template = s"""
  <v-container v-if="item">
    <v-form v-model="valid" ref="fm">
      <v-layout column>
        <v-text-field
          label="Mime Type"
          v-model="item.mimeType"
          :rules=${valRequired("Mime Type")}
          required
        ></v-text-field>
        <v-text-field
          label="Text"
          v-model="item.text"
          :rules=${valRequired("Text")}
          required
          textarea
          auto-grow
        ></v-text-field>
        $chbxRetired 
     </v-layout>
     $formButtons
    </v-form>
  </v-container>"""

}
