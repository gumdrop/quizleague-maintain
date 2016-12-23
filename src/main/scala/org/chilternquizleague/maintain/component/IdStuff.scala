package org.chilternquizleague.maintain.component

import org.chilternquizleague.util.UUID

trait IdStuff[T] {
  def getId(item:T):String
  def newId = UUID.randomUUID().toString()
}