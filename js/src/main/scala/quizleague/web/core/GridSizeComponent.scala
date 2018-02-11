package quizleague.web.core

import com.felstar.scalajs.vue.VuetifyComponent
import scalajs.js
import com.felstar.scalajs.vue.VueComponent
import com.felstar.scalajs.vue.VueRxComponent
import com.felstar.scalajs.vue.VuetifyComponent


trait GridSizeComponentConfig extends Component{
  
  type facade <: VueRxComponent with VuetifyComponent
  
  def gridSize(c:facade) = js.Dictionary("grid-list-sm" -> c.$vuetify.breakpoint.xsOnly, "grid-list-lg" -> c.$vuetify.breakpoint.mdAndUp)
  computed("gridSize")({gridSize _}:js.ThisFunction)

}

