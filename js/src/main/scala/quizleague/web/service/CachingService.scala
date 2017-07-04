package quizleague.web.service

import rxjs.Observable

trait CachingService[T] {
   this: GetService[T] =>
   
   override protected[service] def postProcess(domain: U): U = {items += ((domain.id, domain));domain}
}