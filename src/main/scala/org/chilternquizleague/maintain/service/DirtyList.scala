package org.chilternquizleague.maintain.service

trait DirtyList[T] extends EntitySave[T]{
  this:EntityService[T] =>

  var dirtyIds = Set[String]()
  
  override def cache(item:T) = {
    dirtyIds = dirtyIds + getId(item)
    super.cache(item)
  }
  
  override def save(item: T) = {
    dirtyIds = dirtyIds - getId(item)
    super.save(item)
  }
  
  def saveAllDirty() = {
    dirtyIds.map(getDom(_)).foreach {saveDom(_)}
    dirtyIds = Set()
  }
    
}