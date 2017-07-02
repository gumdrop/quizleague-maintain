package quizleague.web.maintain.component

import quizleague.web.service.EntityService
import angulate2.router.ActivatedRoute
import angulate2.common.Location
import scala.scalajs.js
import js.annotation.ScalaJSDefined
import angulate2.core.OnInit
import js.annotation.JSExport
import quizleague.web.service.PutService
import quizleague.web.service.GetService
import quizleague.web.util.Logging._
import quizleague.web.util.rx.RefObservable
import scala.language.implicitConversions
import rxjs.Observable

trait ItemComponent[T] extends OnInit{
  val service: GetService[T] with PutService[T]
  val route: ActivatedRoute
  val location: Location
  @JSExport
  val utils = ComponentFnUtils

  @JSExport
  var item: T = _

  @JSExport
  def save(): Unit = {service.save(item); location.back() }

  @JSExport
  def cancel(): Unit = { service.flush(); location.back() }

  @JSExport
  def ngOnInit() = init()
  
  def init(): Unit = loadItem().subscribe(x => this.item = x)
  

  protected def loadItem(depth: Int = 1) = route.params
    .switchMap((params, i) => service.get(params("id")))

}

object ItemComponent {

  implicit def wrapArray[T](list: js.Array[RefObservable[T]]) = new ArrayWrapper(list)

  class ArrayWrapper[T](list: js.Array[RefObservable[T]]) {
    def ---=(id: String) = list --= list.filter(_.id == id)
    def +++=(id:String, item:T) = list += RefObservable(id, Observable.of(item))
  }
  
  

}