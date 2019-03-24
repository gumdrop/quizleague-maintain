package quizleague.web.site.chat

import org.scalajs.dom
import quizleague.web.core.{Component, DialogComponentConfig, IdComponent}
import quizleague.web.site.chat.ChatComponent.subscription
import quizleague.web.site.user.{SiteUserService, SiteUserWatchService}
import rxscalajs.Observable

object ChatComponent extends Component{
  type facade = IdComponent
  val name = "ql-chat"
  val template = """
  <v-layout column>
    <v-flex>
     <v-timeline>
      <v-timeline-item
        v-for="message in messages"
        :key="message.id"
        large >
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
    </v-flex>
    <v-flex>
      <transition name="height">
      <div v-if="user" >
        <v-textarea placeholder="Chat Here"
          solo
          outline
          auto-grow
          rows="1" v-on:keypress="console.log($event)"
          append-icon="send"></v-textarea>
       </div>
      </transition>
    </v-flex>
  </v-layout>"""

  prop("id")
  subscription("messages")(c => ChatService.get(c.id).flatMap(_.messages))
  subscription("user")(c => SiteUserWatchService.siteUser)

}

object Chat


object ChatButton extends Component with DialogComponentConfig{
  val name = "ql-chat-button"
  val template="""
  <div v-if="!user">
    <v-dialog persistent v-model="chatLogin" lazy max-width="40%" v-bind="dialogSize" >
      <v-card>
        <v-card-title class="primary"><h5 class="display-1 white--text font-weight-light">Log In</h5>
        </v-card-title>
        <v-card-text>
        <v-text-field type="email" label="email address"></v-text-field>
        </v-card-text>
        <v-card-actions><v-btn @click="chatLogin=false">Submit</v-btn></v-card-actions>
      </v-card>
    </v-dialog>
    <v-tooltip left >
      <v-btn color="primary" fab small v-on:click.stop="chatLogin=true" slot="activator">
        <v-icon>chat</v-icon>
       </v-btn>
      <span>Start Chat!</span>
    </v-tooltip>
  </div>
  """
  data("chatLogin",false)
  subscription("user")(c => SiteUserWatchService.siteUser)
}
