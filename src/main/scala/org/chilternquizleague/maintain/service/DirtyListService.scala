package org.chilternquizleague.maintain.service

import org.chilternquizleague.maintain.component.ComponentNames

trait DirtyListService[T] extends PutService[T]{
  this:GetService[T] with ComponentNames =>

  var dirtyIds = Set[String]()
  
  override def cache(item:T) = {
    dirtyIds = dirtyIds + getId(item)
    super.cache(item)
  }
  
  override def save(item: T) = {
    dirtyIds = dirtyIds - getId(item)
    super.save(item)
  }
  
  override def flush() = {
    dirtyIds = Set()
    items = Map()
  }
  
  override def instance() = {
    val item = super.instance()
    dirtyIds = dirtyIds + getId(item)
    item
  }
  
  def saveAllDirty() = {
    dirtyIds.map(getDom(_)).foreach {saveDom(_)}
    dirtyIds = Set()
  }
    
}