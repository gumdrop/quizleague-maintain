package quizleague.web.service

import quizleague.web.util.Logging._
import quizleague.web.model.Model

trait CachingService[T <: Model] {
   this: GetService[T] =>
   
   override protected[service] def postProcess(item: T): T = {items += ((item.id, item));item}
}