package quizleague.web.service

import quizleague.web.model._

trait RetiredFilter[T <: Model] {
  this:GetService[T] =>
    
    override def filterList(u:U) = !u.retired
}