package quizleague.web.maintain.component

import com.felstar.scalajs.vue.VueRxComponent
import scalajs.js
import js.JSConverters._
import quizleague.web.service.PutService
import quizleague.web.core.IdComponent
import quizleague.web.service.GetService
import quizleague.web.core.Component
import quizleague.web.model.Model
import quizleague.web.names.ComponentNames
import rxscalajs.Observable
import quizleague.web.util.rx.RefObservable

@js.native
trait ItemComponent[T] extends VueRxComponent{
  val valid:Boolean
  val item:T
}

trait ItemComponentConfig[T <: Model] extends Component{
  type facade <: ItemComponent[T]
  
  val paramName = "id"
  
  val service:GetService[T] with PutService[T]
  
  def editText(c:facade, textId:String) = {
      c.$router.push(s"/maintain/text/$textId")
  }
  
  def obsFromParam[X <: Model](c:facade, param:String, service:GetService[X] with PutService[X] = service) = service.get(c.$route.params(param)).map(i => service.cache(i))
  
  def save(c:facade) = {service.save(c.item);c.$router.back()}
  def cancel(c:facade) = {service.flush();c.$router.back()}
  
  subscription("item")(c => obsFromParam(c,paramName))

  method("save")({save _}:js.ThisFunction)
  method("cancel")({cancel _}:js.ThisFunction)
  method("editText")({editText _}:js.ThisFunction)

  data("valid",false)
}

@js.native
trait ItemListComponent[T <: Model] extends VueRxComponent{
  val valid:Boolean
  val item:T
}


trait ItemListComponentConfig[T <: Model] extends Component{

  this:ComponentNames =>
    
  type facade <: ItemListComponent[T]
 
  def sort(items:js.Array[T]) = items.sortBy(_.id)
  
  val service:GetService[T] with PutService[T]
  
  subscription("items")(c => service.list().map(sort _))
  
  method("add")({(c:facade) => {
    val i = service.instance()
    c.$router.push(s"$typeName/${i.id}")}}:js.ThisFunction)
}

object ItemComponentConfig {
  import scala.language.implicitConversions

  implicit def wrapArray[T](list: js.Array[RefObservable[T]]) = new ArrayWrapper(list)

  class ArrayWrapper[T](list: js.Array[RefObservable[T]]) {
    def ---=(id: String):js.Array[RefObservable[T]] = list --= list.filter(_.id == id)
    def +++=(id:String, item:T):js.Array[RefObservable[T]] = {list.push(RefObservable(id, () => Observable.of(item)));list}
  }
  
  

}