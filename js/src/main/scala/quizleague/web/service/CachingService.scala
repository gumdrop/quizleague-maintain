package quizleague.web.service

import quizleague.web.util.Logging._

trait CachingService[T] {
   this: GetService[T] =>
   
   override protected[service] def postProcess(domain: U): U = {items += ((domain.id, domain));domain}
}