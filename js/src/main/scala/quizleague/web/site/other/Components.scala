package quizleague.web.site.other

import quizleague.web.core._
import quizleague.web.site._

object LinksComponent extends RouteComponent with NoSideMenu with GridSizeComponentConfig{
  val template = """
  <v-container v-bind="gridSize" fluid>
    <v-layout>
    <v-flex><ql-text-box><ql-named-text name="links-content"></ql-named-text></ql-text-box></v-flex>
    </v-layout>
  </v-container>"""  
}

object LinksTitleComponent extends RouteComponent{
  val template = """<v-toolbar      
      color="green lighten-3"
      dense
      class="subtitle-background"
      >
      <ql-title>Links</ql-title>
      <v-toolbar-title>
        Links
      </v-toolbar-title>
    </v-toolbar>"""
}

object RulesComponent extends RouteComponent with NoSideMenu with GridSizeComponentConfig{
  val template = """
  <v-container v-bind="gridSize" fluid>
  <ql-title>Rules</ql-title>
    <v-layout>
    <v-flex><ql-text-box><ql-named-text name="rules-content"></ql-named-text></ql-text-box></v-flex>
    </v-layout>
  </v-container>"""  
}

object ContactUsComponent extends RouteComponent with NoSideMenu with GridSizeComponentConfig{
  val template = """
  <v-container v-bind="gridSize" fluid>
  <ql-title>Contact Us</ql-title>
    <v-layout>
    <v-flex><ql-text-box><ql-named-text name="contact-content"></ql-named-text></ql-text-box></v-flex>
    </v-layout>
  </v-container>"""  
}

