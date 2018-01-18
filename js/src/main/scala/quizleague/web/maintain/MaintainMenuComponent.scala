package quizleague.web.maintain

import quizleague.web.core._

object MaintainMenuComponent extends RouteComponent{
    val template = """
  <v-container>
    <v-layout column>
      <v-btn flat to="/maintain/applicationcontext" toActive="active">Application Context</v-btn>
      <v-btn flat to="/maintain/globaltext" toActive="active">Global Text</v-btn>
      <v-btn flat to="/maintain/team" toActive="active">Teams</v-btn>
      <v-btn flat to="/maintain/season"  toActive="active">Seasons</v-btn>
      <v-btn flat to="/maintain/user" toActive="active">Users</v-btn>
      <v-btn flat to="/maintain/venue" toActive="active">Venues</v-btn>
      <v-btn flat to="/maintain/database"  toActive="active">Database</v-btn>
    </v-layout>
  </v-container>
"""
  
}

