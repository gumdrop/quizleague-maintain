package quizleague.web.service

import quizleague.web.names.ComponentNames
import quizleague.web.model.{Key, Model}
import rxscalajs.Observable

trait DirtyListService[T <: Model] extends PutService[T] {
  this: GetService[T] with ComponentNames =>

  var dirtyKeys = Set[Key]()
  var deleteKeys = Set[Key]()

  override def cache(item: T) = {
    dirtyKeys = dirtyKeys + item.key
    super.cache(item)
  }
  
  override def delete(id:String) = {
    dirtyKeys = dirtyKeys - key(null,id)
    deleteKeys = deleteKeys + key(null,id)
    Observable.just(null)
  }

  override def delete(item: T) = {
    dirtyKeys = dirtyKeys - item.key
    deleteKeys = deleteKeys + item.key
    Observable.just(null)
  }

  override def deCache(item: U) = {
    dirtyKeys = dirtyKeys - key(item.key)
    super.deCache(item)
  }

  override def flush() = {
    dirtyKeys = Set()
    items.clear()
  }

  override def instance() = {
    val item = super.instance()
    dirtyKeys = dirtyKeys + getKey(item)
    item
  }

  override def filterList(u: U): Boolean = !deleteKeys.contains(key(u.key))

  def saveAllDirty() = {
    dirtyKeys.map(k => getDom(k.id)).foreach { save(_) }
    deleteKeys.foreach {doDelete _}
    deleteKeys = Set()
    deleteKeys = Set()
  }

}