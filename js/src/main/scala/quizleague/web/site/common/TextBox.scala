package quizleague.web.site.common

import quizleague.web.core.Component

object TextBox extends Component {
  val name = "ql-text-box"
  val template = """<v-sheet elevation="1" class="pa-4" ><slot></slot></v-sheet>"""
}
