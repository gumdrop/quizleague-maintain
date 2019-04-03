package quizleague.web.site.chat

import java.time.LocalDateTime

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
  <div >
    <v-tooltip left v-if="!user">
      <v-btn color="primary" fab small v-on:click.stop="login=true" slot="activator">
        <v-icon>{{icon}}</v-icon>
       </v-btn>
      <span>{{label}}</span>
    </v-tooltip>
    <v-dialog persistent v-model="login" lazy max-width="40%" v-bind="dialogSize" >
      <v-card>
        <v-form>
          <v-card-title class="primary"><h5 class="display-1 white--text font-weight-light">Log In</h5>
          </v-card-title>
          <v-card-text>
            <v-text-field type="email" label="email address" v-model="email"></v-text-field>
          </v-card-text>
          <v-card-actions><v-btn @click="loginToSite(email);login=false;profile=true">Login</v-btn></v-card-actions>
        </v-form>
      </v-card>
    </v-dialog>
    <v-dialog v-if="user" persistent v-model="profile" lazy max-width="40%" v-bind="dialogSize" >
      <v-card>
      <v-form>
        <v-card-title class="primary"><h5 class="display-1 white--text font-weight-light">Profile Settings</h5>
        </v-card-title>
        <v-card-text>
          <v-layout column>
            <v-text-field type="text" label="Handle" v-model="user.handle"></v-text-field>
            <v-text-field type="url" label="Avatar" v-model="user.avatar">
              <template slot="append" size="36">
                <v-avatar><img :src="user.avatar"></v-avatar>
              </template>
            </v-text-field>
          </v-layout>
        </v-card-text>
        <v-card-actions><v-btn @click="saveUser(user);profile=false">Save</v-btn><v-spacer></v-spacer><v-btn @click="profile=false">Cancel</v-btn></v-card-actions>
      </v-card>
      </v-form>
    </v-dialog>
  </div>
  """
  prop("label")
  prop("icon")
  data("login",false)
  data("profile",false)
  data("email", null)
  subscription("user")(c => SiteUserWatchService.siteUser)

  method("loginToSite")({login _}:js.ThisFunction)
  method("create")({create _}:js.ThisFunction)
  method("saveUser"){user:SiteUser => SiteUserService.saveUser(user)}

  def login(c:facade,email:String) = {
    SiteUserService.siteUserForEmail(email).subscribe(su => su.headOption.map( u => SiteUserWatchService.setSiteUserID(u.id)).getOrElse({c.login = true;c.profile=true}))
  }

  def create(c:facade) = if(c.id.isEmpty){ChatService.add(c.parentKey)}


}
