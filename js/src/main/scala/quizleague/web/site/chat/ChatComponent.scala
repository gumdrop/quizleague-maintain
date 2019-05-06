package quizleague.web.site.chat

import java.time.LocalDateTime

import com.felstar.scalajs.vue.VueRxComponent
import org.scalajs.dom
import quizleague.web.core.{Component, DialogComponent, DialogComponentConfig}
import quizleague.web.model._
import quizleague.web.site.login.LoginService
import quizleague.web.site.user.{SiteUserService, SiteUserWatchService}
import quizleague.web.util.rx.RefObservable
import rxscalajs.Observable

import scala.scalajs.js

@js.native
trait ChatComponent extends VueRxComponent {
  val parentKey:String
  val chat:js.UndefOr[Chat]
  var messagesObs:js.UndefOr[Observable[js.Array[ChatMessage]]]
  val user:SiteUser
}

object ChatComponent extends Component{
  type facade = ChatComponent
  val name = "ql-chat"
  val template = """
  <v-layout column>
    <v-flex v-if="chat">
    <ql-chat-messages  :chatID="chat.id" :parentKey="parentKey"></ql-chat-messages>
    </v-flex>
    <v-flex>
      <div v-if="user" >
        <v-textarea placeholder="Chat Here"
          solo
          outline
          auto-grow
          v-model="text"
          rows="1">

          <template slot="append"><v-btn fab flat :disabled="!text" @click="text = addMessage(text)"><v-icon>send</v-icon></v-btn></template>

        </v-textarea>
       </div>
    </v-flex>
  </v-layout>"""

  components(ChatMessages)

  prop("parentKey")
  data("text",null)
  subscription("chat", "parentKey")(c => ChatService
    .list(c.parentKey)
    .flatMap(chats =>
      chats.headOption.fold
      (Observable.from(js.Array[Chat]()))
      (chat => Observable.just(chat))))
  subscription("user")(c => LoginService.userProfile.map(_.siteUser))
  method("addMessage")({addMessage _}:js.ThisFunction)


  def addMessage(c:facade, text:String) = {

    def saveMessage(chatID:String): Unit ={
      ChatMessageService.saveMessage(text, c.user.id, chatID, c.parentKey).subscribe(x => x)
    }

    if(c.chat.isEmpty){
      ChatService.add(c.parentKey).subscribe(saveMessage _)
    }
    else{
      saveMessage(c.chat.get.id)
    }


    null
  }

}

@js.native
trait ChatMessages extends VueRxComponent {
  val chatID:String
  val parentKey:String
}

object ChatMessages extends Component{

  type facade = ChatMessages

  val name = "ql-chat-messages"
  val template = """
    <v-timeline v-if="messages">
      <v-timeline-item :left="isLeft(message)" :right="!isLeft(message)"
        v-for="message in messages"
        :key="message.id"
        small >
        <template v-slot:icon>
          <v-avatar>
            <img :src="async(message.user).avatar">
          </v-avatar>
        </template>
        <template v-slot:opposite>
          <span>{{async(message.user).handle}}</span>
        </template>
        <v-card class="elevation-2">
          <v-card-text>{{message.message}}</v-card-text>
        </v-card>
      </v-timeline-item>
    </v-timeline>
  """

  props("parentKey","chatID")

  subscription("messages","chatID")(c => ChatMessageService.list(c.parentKey,c.chatID))

  method("isLeft")({c:ChatMessage => c.user.id != SiteUserWatchService.getSiteUserID() })
  method("date"){d:String => LocalDateTime.parse(d).toLocalDate.toString}
  method("time"){d:String => LocalDateTime.parse(d).toLocalTime.toString}

}

@js.native
trait LoginButton extends VueRxComponent {
  var login:Boolean = false
  var profile:Boolean = false
  val id:js.UndefOr[String]
  val parentKey:String
  val user:SiteUser
}

object LoginButton extends Component with DialogComponentConfig{

  type facade = LoginButton with DialogComponent

  val name = "ql-login-button"
  val template="""
  <div v-if="!user">
    <v-tooltip left>
      <v-btn color="primary" fab small :to="'/login?forward=' + window.location.pathname" slot="activator">
        <v-icon>mdi-login</v-icon>
       </v-btn>
      <span>{{label}}</span>
    </v-tooltip>
  </div>
  """
  prop("label")
  subscription("user")(c => LoginService.userProfile)

}
