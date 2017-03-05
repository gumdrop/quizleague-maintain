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
import quizleague.web.util.Logging

trait ItemComponent[T] extends OnInit with Logging{
  val service:GetService[T] with PutService[T]
  val route:ActivatedRoute
  val location:Location
  

  @JSExport
  var item:T = _
  
  @JSExport
  def save():Unit = {service.save(item);location.back()}
  
  @JSExport
  def cancel():Unit = {service.flush();location.back()}

  @JSExport
  def ngOnInit() = init()
  
  def init(): Unit = loadItem().subscribe( x=> {log(null, "before load item");this.item = x ; log(item,"loaded item")})
    
  protected def loadItem(depth:Int = 1) = route.params
    .switchMap( (params,i) => service.get(params("id"))(depth))
}

