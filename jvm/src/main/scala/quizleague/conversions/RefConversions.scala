package quizleague.conversions

import quizleague.domain.Ref
import scala.reflect.ClassTag
import quizleague.data.Storage
import io.circe.Decoder
import quizleague.domain.Entity
import scala.language.implicitConversions

object RefConversions {
  
  implicit def refToObject[T <: Entity](ref:Ref[T])(implicit tag:ClassTag[T],decoder: Decoder[T]) = Storage.load(ref.id)
    
  
}