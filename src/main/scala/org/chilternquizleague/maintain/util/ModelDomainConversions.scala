package org.chilternquizleague.maintain.util

import org.chilternquizleague.domain.Entity

object ModelDomainConversions {
  import scala.language.implicitConversions
  import org.chilternquizleague.web.model.Ref
  import org.chilternquizleague.domain.{Ref => DomRef}
  
  implicit def refToRef[T <: Entity](ref:Ref) = DomRef[T](ref.typeName, ref.id)
}