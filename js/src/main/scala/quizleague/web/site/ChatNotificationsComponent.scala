package quizleague.web.site

import java.time.{ZoneOffset, ZonedDateTime}

import com.felstar.scalajs.vue.VueRxComponent
import quizleague.web.core.Component
import quizleague.web.model.SiteUser
import quizleague.web.site.login.{LoggedInUser, LoginService}
import quizleague.web.site.user.SiteUserService

import scala.scalajs.js

@js.native
trait ChatNotificationsComponent extends VueRxComponent {
  var now: ZonedDateTime
  var messages: Boolean
  var user: LoggedInUser
}

object ChatNotificationsComponent extends Component {

  type facade = ChatNotificationsComponent

  val name = "chat-notifications"

  val template = """
         <div v-if="user">
           <v-snackbar
              timeout="100000000"
              :bottom="true"
              :right="true"
              v-model="messages"
              color="error"
            >

            <div v-for="chat in chats" >{{chat.message}}</div>
            <v-btn icon text dark @click.native="messages = false"><v-icon right>mdi-close</v-icon</v-btn>
            </v-snackbar>
          </div>
"""

  def now = ZonedDateTime.now(ZoneOffset.UTC)
  def threshold(c:facade) = now.minusMonths(1)
  //def remove(c:facade) = NotificationService.


  prop("user")
  data("now", now)
  data("messages", false)
 // method("remove")()
  subscription("chats", "user","now")(c => NotificationService.chatMessages(threshold(c), c.user.siteUser).map(m => { c.messages = true; m }))

}

