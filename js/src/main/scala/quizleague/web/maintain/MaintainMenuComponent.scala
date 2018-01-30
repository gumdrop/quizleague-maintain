package quizleague.web.maintain

import quizleague.web.core._

object MaintainMenuComponent extends Component{
  
  val name = "ql-maintain-menu"
  
  val template = """
     <v-list >
        <v-list-tile to="/maintain/applicationcontext"><v-list-tile-content><v-list-tile-title>Application Context</v-list-tile-title></v-list-tile-content></v-list-tile>
        <v-list-tile to="/maintain/globaltext"><v-list-tile-content><v-list-tile-title>Global Text</v-list-tile-title></v-list-tile-content></v-list-tile>
        <v-list-tile to="/maintain/team"><v-list-tile-content><v-list-tile-title>Teams</v-list-tile-title></v-list-tile-content></v-list-tile>
        <v-list-tile to="/maintain/season"><v-list-tile-content><v-list-tile-title>Seasons</v-list-tile-title></v-list-tile-content></v-list-tile>
        <v-list-tile to="/maintain/user"><v-list-tile-content><v-list-tile-title>Users</v-list-tile-title></v-list-tile-content></v-list-tile>
        <v-list-tile to="/maintain/venue"><v-list-tile-content><v-list-tile-title>Venues</v-list-tile-title></v-list-tile-content></v-list-tile>        
        <v-list-tile to="/maintain/database"><v-list-tile-content><v-list-tile-title>Database</v-list-tile-title></v-list-tile-content></v-list-tile>        
     </v-list>
"""
  
}

