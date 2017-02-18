package quizleague.web.maintain.util

import quizleague.domain.Entity

object ModelDomainConversions {
  import scala.language.implicitConversions
  import quizleague.web.model.Ref
  import quizleague.domain.{Ref => DomRef}
  
  implicit def refToRef[T <: Entity](ref:Ref) = DomRef[T](ref.typeName, ref.id)
}