package org.chilternquizleague.maintain.service

import scalajs.js
import org.chilternquizleague.util.UUID
import org.chilternquizleague.maintain.domain.Ref
import org.chilternquizleague.maintain.component.ComponentNames

trait PutService[T] {
  this: GetService[T] with ComponentNames=>

  def cache(item: T) = add(mapIn(item))
  
  def save(item: T) = saveDom(log(mapIn(item), "save - mapIn : "))
  
  protected def saveDom(i:U) = {
    http.put(s"$uriRoot/${i.id}", log(wrap(i), "save - toJson : "), requestOptions)
    deCache(i)
  }
  
  
  def getRef(item:T):Ref[U] = Ref(typeName,getId(item))
  def delete(item:T) = {items = items - mapIn(item).id} 
  def instance() = add(make())
  def getId(item:T) = if (item != null ) mapIn(item).id else null
  protected final def newId() = UUID.randomUUID.toString()
  private[service] def wrap(item:U) = js.Dynamic.literal(id = item.id, json = toJson(item))
  private[service] def deCache(item:U) = items = items - item.id
  private def toJson(item:U) = if(item != null) ser(item) else null

  
  protected def mapIn(model:T):U
  protected def make():U
  protected def ser(item:U):String


}