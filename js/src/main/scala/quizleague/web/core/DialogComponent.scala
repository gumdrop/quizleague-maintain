package quizleague.web.core

import com.felstar.scalajs.vue.VuetifyComponent
import scalajs.js
import com.felstar.scalajs.vue.VueComponent
import com.felstar.scalajs.vue.VueRxComponent
import com.felstar.scalajs.vue.VuetifyComponent

@js.native
trait DialogComponent extends VueRxComponent with VuetifyComponent{
  var show:Boolean
}

trait DialogComponentConfig extends Component{

  type facade <: DialogComponent
  
  def dialogSize(c:facade) = js.Dictionary("fullscreen" -> c.$vuetify.breakpoint.smAndDown)
  
  prop("show")
  computed("dialogSize")({dialogSize _}:js.ThisFunction)
  watch("show")((c:facade, a:js.Any) => c.$emit("show",c.show))
  method("close")({(c:facade) => c.show = false}:js.ThisFunction)
}