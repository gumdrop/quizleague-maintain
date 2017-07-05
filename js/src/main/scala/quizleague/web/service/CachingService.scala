package quizleague.web.service

import quizleague.web.util.Logging._

trait CachingService[T] {
   this: GetService[T] =>
   
   override protected[service] def postProcess(domain: U): U = {items += ((domain.id, domain));log(items, s"${domain.getClass.getSimpleName} cache");domain}
}