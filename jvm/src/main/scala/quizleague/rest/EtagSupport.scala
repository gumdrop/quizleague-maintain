package quizleague.rest

import javax.ws.rs.core.Request
import javax.ws.rs.core.HttpHeaders

trait EtagSupport {
  val request:Request
  val headers:HttpHeaders
  
  
}