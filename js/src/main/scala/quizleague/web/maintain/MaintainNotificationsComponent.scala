package quizleague.web.maintain

import quizleague.web.core._
import java.time.LocalDateTime
import com.felstar.scalajs.vue.VueRxComponent
import scalajs.js

@js.native
trait MaintainNotificationsComponent extends VueRxComponent {
  var now: LocalDateTime
  var messages: Boolean
}

object MaintainNotificationsComponent extends Component {

  type facade = MaintainNotificationsComponent
  
  val name = "notifications"

  val template = """
         <v-snackbar
            timeout="60000000"
            :bottom="true"
            :right="true"
            v-model="messages"
          >
          {{notification}}
          <v-btn text color="pink" @click.native="messages = false">Close</v-btn>
          </v-snackbar>
"""

  data("now", LocalDateTime.now)
  data("messages", false)
  subscription("notification", "now")(c => NotificationService.messages(c.now).map(m => { c.messages = true; m }))
  watch("messages")((c: facade, value: js.Any) => if (!c.messages) { c.now = LocalDateTime.now })

}