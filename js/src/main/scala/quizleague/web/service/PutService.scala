package quizleague.web.service

import scalajs.js
import quizleague.web.util.UUID
import quizleague.domain.Ref
import quizleague.web.maintain.component.ComponentNames

trait PutService[T] {
  this: GetService[T] with ComponentNames=>

  def cache(item: T) = add(mapIn(item))
  
  def save(item: T):Unit = save(mapIn(item))
  
  protected def save(item:U):Unit = saveDom(item)
  
  private[service] def saveDom(i:U) = {
    http.put(s"$uriRoot/${i.id}", wrap(i), requestOptions).subscribe(x=>x)
    log(i,s"saved $uriRoot/${i.id} to http")
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