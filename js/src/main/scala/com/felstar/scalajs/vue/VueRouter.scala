package com.felstar.scalajs.vue

import scala.scalajs.js
import js.Dynamic.literal
import js.|
import scala.scalajs.js.JSConverters._
import org.scalajs.dom
import org.scalajs.dom._

import js.annotation._

@JSGlobal("VueRouter")
@js.native
class Router extends js.Object {
  def this(obj: js.Any) = this()
  val routes:js.Array[js.Any] = js.native
  val mode:String = js.native
  val base:String = js.native
  val linkActiveClass:String = js.native
  val linkExactActiveClass:String = js.native
  val scrollBehavior:js.Function = js.native

}


object Router{
  def apply(
  routes:js.Array[js.Any],
  mode:String = "history",
  base:String = "/",
  linkActiveClass:String = null,
  linkExactActiveClass:String = null,
  scrollBehavior:js.Function = null
  ) = new Router(literal(
    routes=routes.toJSArray,
    mode=mode,
    base=base,
    linkActiveClass=linkActiveClass,
    linkExactActiveClass=linkExactActiveClass,
    scrollBehavior = scrollBehavior
  ))
}

//@JSGlobal
//@js.native
//class RouteConfig extends js.Object{
//  def this(obj: js.Any) = this()
//  val path: String = js.native
//  val component: js.Any= js.native
//  val name: String = js.native
//  val components: js.Array[js.Any] = js.native
//  val redirect: js.Any= js.native
//  val props: js.Any = js.native
//  val alias: js.Any = js.native
//  val children: js.Array[RouteConfig] = js.native
//  val beforeEnter: js.Function3[js.Any, js.Any, js.Function1[Boolean|String|Exception, Unit], Unit] = js.native
//}

@js.native
trait RouterInstance extends js.Object{
  def back():Unit = js.native
  def push(path:String):Unit = js.native
}







