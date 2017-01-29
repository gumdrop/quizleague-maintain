package org.chilternquizleague.maintain.util

import org.chilternquizleague.maintain.domain.Entity

object ModelDomainConversions {
  import scala.language.implicitConversions
  import org.chilternquizleague.maintain.model.Ref
  import org.chilternquizleague.maintain.domain.{Ref => DomRef}
  
  implicit def refToRef[T <: Entity](ref:Ref) = DomRef[T](ref.typeName, ref.id)
}