package quizleague.web.model

import scalajs.js
import quizleague.domain.{Key => DomKey}

abstract class Model extends js.Object {
  val id:String
  var key:Key = null
}


class Key(val parentKey:String, val entityName:String, val id:String) extends js.Object {
  def key = s"${Option(parentKey).fold("")(x =>s"$x/")}$entityName/$id"
}

object Key{
  def apply(domKey:DomKey) = new Key(domKey.parentKey.getOrElse(null), domKey.entityName, domKey.id)
  def apply(key:String) = {
    val parts = key.split('/')
    val length = parts.length
    val end = parts.takeRight(2)
    new Key(parts.take(length -2).mkString("/"),end(0),end(1))
  }
}