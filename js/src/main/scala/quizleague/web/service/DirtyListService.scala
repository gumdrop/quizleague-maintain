package quizleague.web.service

import quizleague.web.names.ComponentNames

trait DirtyListService[T] extends PutService[T] {
  this: GetService[T] with ComponentNames =>

  var dirtyIds = Set[String]()

  override def cache(item: T) = {
    cache(mapIn(item))
    super.cache(item)
  }

  protected def cache(item: U) = {
    dirtyIds = dirtyIds + item.id
    item
  }

  override def deCache(item: U) = {
    dirtyIds = dirtyIds - item.id
    super.deCache(item)
  }

  override def flush() = {
    dirtyIds = Set()
    items.clear()
  }

  override def instance() = {
    val item = super.instance()
    dirtyIds = dirtyIds + getId(item)
    item
  }

  def saveAllDirty() = {
    dirtyIds.map(getDom(_)).foreach { save(_) }
    dirtyIds = Set()
  }

}