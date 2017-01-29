package org.chilternquizleague.maintain.service

import scalajs.js

trait EntitySave[T] {
  this: EntityService[T] =>

  def cache(item: T) = add(mapIn(item))
  
  def save(item: T) = saveDom(log(mapIn(item), "save - mapIn : "))
  
  protected def saveDom(i:U) = {
    http.put(s"$uriRoot/${i.id}", log(wrap(i), "save - toJson : "), requestOptions)
    deCache(i)
  }

}