package quizleague.web.site.chat

import com.felstar.scalajs.vue.VueRxComponent
import org.scalajs.dom
import quizleague.web.core.{Component, DialogComponent, DialogComponentConfig}
import quizleague.web.model._
import quizleague.web.site.user.{SiteUserService, SiteUserWatchService}
import quizleague.web.util.rx.RefObservable
import rxscalajs.Observable

import scala.scalajs.js

@js.native
trait ChatComponent extends VueRxComponent {
  val parentKey:String
  val chat:js.UndefOr[Chat]
  var messagesObs:js.UndefOr[Observable[js.Array[ChatMessage]]]
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
          rows="1"
          append-icon="send"
          @click:append="text = addMessage(text)"></v-textarea>
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
  subscription("user")(c => SiteUserWatchService.siteUser)
  method("addMessage")({addMessage _}:js.ThisFunction)


  def addMessage(c:facade, text:String) = {

    def saveMessage(chatID:String): Unit ={
      ChatMessageService.saveMessage(text, SiteUserWatchService.getSiteUserID(), chatID, c.parentKey).subscribe(x => x)
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
      <v-timeline-item :left="isLeft(message)" :right="isRight(message)"
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
  method("isRight")({c:ChatMessage => c.user.id == SiteUserWatchService.getSiteUserID() })

}

@js.native
trait ChatButton extends VueRxComponent {
  var login:Boolean = false
  val id:js.UndefOr[String]
  val parentKey:String
}

object ChatButton extends Component with DialogComponentConfig{

  type facade = ChatButton with DialogComponent

  val name = "ql-chat-button"
  val template="""
  <div v-if="!user">
    <v-dialog persistent v-model="login" lazy max-width="40%" v-bind="dialogSize" >
      <v-card>
      <v-form>
        <v-card-title class="primary"><h5 class="display-1 white--text font-weight-light">Log In</h5>
        </v-card-title>
        <v-card-text>
        <v-text-field type="email" label="email address" v-model="email"></v-text-field>
        </v-card-text>
        <v-card-actions><v-btn @click="loginToSite(email);login=false">Submit</v-btn></v-card-actions>
      </v-card>
      </v-form>
    </v-dialog>
    <v-tooltip left >
      <v-btn color="primary" fab small v-on:click.stop="login=true" slot="activator">
        <v-icon>chat</v-icon>
       </v-btn>
      <span>Login</span>
    </v-tooltip>
  </div>
  """
  data("login",false)
  data("email", null)
  subscription("user")(c => SiteUserWatchService.siteUser)

  method("loginToSite")({login _}:js.ThisFunction)
  method("create")({create _}:js.ThisFunction)

  def login(c:facade,email:String) = {
    SiteUserService.siteUserForEmail(email).subscribe(su => su.headOption.map( u => SiteUserWatchService.setSiteUserID(u.id)).getOrElse(c.login = true))
  }

  def create(c:facade) = if(c.id.isEmpty){ChatService.add(c.parentKey)}
}
