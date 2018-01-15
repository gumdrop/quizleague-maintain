package quizleague.web.maintain

import quizleague.web.core._

object MaintainMenuComponent extends RouteComponent{
    val template = """
  <v-container>
    <v-layout column>
      <v-btn flat to="/maintain/applicationcontext" md-button toActive="active">Application Context</v-btn>
      <v-btn flat to="/maintain/globaltext" md-button toActive="active">Global Text</v-btn>
      <v-btn flat to="/maintain/team" md-button toActive="active">Teams</v-btn>
      <v-btn flat to="/maintain/season" md-button toActive="active">Seasons</v-btn>
      <v-btn flat to="/maintain/user" md-button toActive="active">Users</v-btn>
      <v-btn flat to="/maintain/venue" md-button toActive="active">Venues</v-btn>
    </v-layout>
  </v-container>
"""
  
}

