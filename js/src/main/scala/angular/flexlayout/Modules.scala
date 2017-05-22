package angular.flexlayout

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport
import rxjs.Observable
import rxjs.Subscribable

@js.native
@JSImport("@angular/flex-layout","FlexLayoutModule")
class FlexLayoutModule extends js.Object

@js.native
@JSImport("@angular/flex-layout","MediaChange")
class MediaChange extends js.Object{
  val matches:Boolean = js.native
  val mediaQuery:String = js.native
  val mqAlias:String = js.native
  val suffix:String = js.native  
  
}

@js.native
@JSImport("@angular/flex-layout","ObservableMedia")
class ObservableMedia extends Subscribable[MediaChange]

