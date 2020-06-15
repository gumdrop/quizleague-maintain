package quizleague.web.model

import scalajs.js
import quizleague.domain.{Key => DomKey}

import scala.scalajs.js.URIUtils

abstract class Model extends js.Object {
  val id:String
  var key:Key = null
}


class Key(val parentKey:String, val entityName:String, val id:String) extends js.Object {
  def key = s"${Option(parentKey).fold("")(x =>s"$x/")}$entityName/$id"

  def encode = key.replace('/','|')

  override def toString: String = key
  override def hashCode():Int = key.hashCode
}

object Key{
  def apply(domKey:DomKey):Key = new Key(domKey.parentKey.getOrElse(null), domKey.entityName, domKey.id)
  def apply(key:String):Key = {
    val parts = key.split('/')
    val length = parts.length
    val end = parts.takeRight(2)
    new Key(parts.take(length -2).mkString("/"),end(0),end(1))
  }
  def apply(key:Option[DomKey]):Key = key.map(Key(_)).getOrElse(null)
  def decode(key:String) = Key(key.replace('|','/'))
}