package quizleague.web.site

import quizleague.web.core._
import java.time.{LocalDateTime, ZoneId, ZoneOffset, ZonedDateTime}

import com.felstar.scalajs.vue.VueRxComponent

import scalajs.js

@js.native
trait ResultNotificationsComponent extends VueRxComponent {
  var now: ZonedDateTime
  var messages: Boolean
}

object ResultNotificationsComponent extends Component {

  type facade = ResultNotificationsComponent
  
  val name = "notifications"

  val template = """
         <v-snackbar
            timeout="10000"
            :bottom="true"
            :right="true"
            v-model="messages"
            color="info"
          >
          <ql-fixtures-simple :fixtures="fixtures"></ql-fixtures-simple>
          <v-btn icon text dark @click.native="messages = false"><v-icon right>mdi-close</v-icon</v-btn>
          </v-snackbar>
"""

  def now = ZonedDateTime.now

  data("now", now)
  data("messages", false)
  subscription("fixtures", "now")(c => NotificationService.resultMessages(c.now).map(m => { c.messages = true; m }))
  watch("messages")((c: facade, value: js.Any) => if (!c.messages) { c.now = now })

}