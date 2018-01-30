package quizleague.web.maintain

import com.felstar.scalajs.vue._
import scalajs.js
import quizleague.web.core._

import quizleague.web.site.common._



object MaintainAppModule extends Module {
  
  override val modules = @@(CommonModule, MaintainModule)
  
 
  override val components = @@(MaintainAppComponent)
   
}



