package quizleague.web.site.chat

import java.time.LocalDateTime

import com.felstar.scalajs.vue.VueRxComponent
import quizleague.util.collection._
import quizleague.web.core._
import quizleague.web.model._
import quizleague.web.site.login.{LoggedInUser, LoginService}
import rxscalajs.Observable

import scala.scalajs.js
import scala.scalajs.js.UndefOr

@js.native
trait ChatComponent extends VueRxComponent {
  val parentKey:Key
  val chat:js.UndefOr[Chat]
  var messagesObs:js.UndefOr[Observable[js.Array[ChatMessage]]]
  val user:SiteUser
  var text:String
  val name:String
}

object ChatComponent extends Component{
  type facade = ChatComponent
  val name = "ql-chat"
  val template = """
  <v-layout column>
    <v-flex class="pb-0">
      <div v-if="user" >
        <v-textarea label="Your message here"
          :clearable="false"
          solo
          outline
          auto-grow
          v-model="text"
          hide-details
          rows="1"
          >
          <template v-slot:append v-if="text" >
            <v-tooltip top >
              <template v-slot:activator="{ on }">
                <v-btn v-on="on" small icon @click="addMessage(text)" style="top:-5px;">
                <v-icon color="primary">mdi-send</v-icon>
                </v-btn>
              </template>
              <span>Send</span>
            </v-tooltip>
          </template>
        </v-textarea>
       </div>
    </v-flex>
    <v-flex v-if="chat" class="pt-0">
      <ql-chat-messages  :chatKey="chat.key" ></ql-chat-messages>
    </v-flex>
  </v-layout>"""

  components(ChatMessages)

  prop("parentKey")
  prop("name")
  data("text",null)
  subscription("chat", "parentKey")(c => ChatService
    .list(c.parentKey)
    .map(chats => chats.headOption.getOrElse(null)))
  subscription("user")(c => LoginService.userProfile.filter(_ != null).map(_.siteUser))
  method("addMessage")({addMessage _}:js.ThisFunction)


  def addMessage(c:facade, text:String) = {

    def saveMessage(chatKey:Key): Unit ={
      ChatMessageService.saveMessage(text, c.user.id, chatKey).subscribe(x => x)
    }

    if(c.chat.filter(_ != null).isEmpty){
      ChatService.add(c.parentKey, c.name).subscribe(saveMessage _)
    }
    else{
      saveMessage(c.chat.get.key)
    }
    c.text = null
  }

}

@js.native
trait ChatMessages extends VueRxComponent {
  val chatKey:Key
  val user:UndefOr[LoggedInUser]
}

object ChatMessages extends Component{

  type facade = ChatMessages

  val name = "ql-chat-messages"
  val template = """
    <v-timeline v-if="messages">
      <v-timeline-item v-bind="align(message)"
        v-for="message in messages"
        :key="message.id"
        small
         transition="fade-transition"
        >
        <template v-slot:icon>
          <v-avatar size="36">
            <img :src="async(message.user).avatar">
          </v-avatar>
        </template>
        <template v-slot:opposite>
          <div>
            <span>{{async(message.user).handle}}</span>
            <div class="caption">{{message.date | datetime('d MMM yyy - hh:mm')}}</div>
          </div>
        </template>
        <v-card class="elevation-2">
          <v-card-text class="pb-1"><ql-markdown :text="message.message" ></ql-markdown></v-card-text>
        </v-card>
      </v-timeline-item>
    </v-timeline>
  """

  props("chatKey")

  subscription("messages","chatKey")(c => ChatService.get(c.chatKey).flatMap(_.messages).map(_.sortBy(_.date)(Desc)))
  subscription("user")(c => LoginService.userProfile)

  method("isLeft")({(c:facade,m:ChatMessage) => c.user.fold(true)(_.siteUser.id != m.user.id)}:js.ThisFunction)
  method("date"){d:String => LocalDateTime.parse(d).toLocalDate.toString}
  method("time"){d:String => LocalDateTime.parse(d).toLocalTime.toString}
  method("align")({(c:facade,m:ChatMessage) => c.user.filter(_!=null).fold(js.Object())(u => if(u.siteUser.id != m.user.id)$(left=true)else $(right=true))}:js.ThisFunction)

}

object HotChats extends Component{
  val name = "ql-hot-chats"
  val template = """
  <ql-text-box>
    <v-layout column>
      <v-flex v-for="chat in chats"><ql-chat :parentKey="parentKey(chat)" name="dummy"></ql-chat></v-flex  >
    </v-layout>
  </ql-text-box>
  """

  subscription("chats")(c => ChatMessageService.hotChats())
  method("parentKey")((chat:ChatMessage) => Key(Key(chat.key.parentKey).parentKey))
  method("chatID")((chat:ChatMessage) => Key(chat.key.parentKey).id)
}


object LoginButton extends Component{

  val name = "ql-login-button"
  val template="""
  <div v-if="!user">
    <v-tooltip left>
     <template #activator="{ on }">
      <v-btn color="primary" fab small :to="'/login?forward=' + window.location.pathname" v-on="on">

        <v-icon>mdi-login</v-icon>
       </v-btn>
     </template>
     <span>{{label}}</span>
    </v-tooltip>
  </div>
  """
  prop("label")
  subscription("user")(c => LoginService.userProfile)

}
