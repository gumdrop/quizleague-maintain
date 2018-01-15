package quizleague.web.core

import scalajs.js
import js.Dynamic.literal
import js.JSConverters._


object RouteConfig{
  def apply(
   path: String,
   component: js.Any = null,
   name: String = null,
   components: Map[String,Component]= null,
   redirect: js.Any= null,
   props: js.Any = null,
   alias: js.Any = null,
   children: js.Array[js.Any] = js.Array()
  ) = literal(
      path = path,
      component = component,
      name = name,
      components = if(components == null) null else components.map{case(k,v) => (k,v())}.toJSDictionary,
      redirect = redirect,
      props = props,
      //alias = alias,
      children = children
  )
}