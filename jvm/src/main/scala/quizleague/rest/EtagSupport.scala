package quizleague.rest

import javax.ws.rs.WebApplicationException
import javax.ws.rs.core.Request
import javax.ws.rs.core.UriInfo

trait EtagSupport {
  val request: Request
  val uriInfo: UriInfo

  def pre = {

    request.getMethod match {
      case "GET" => {
        val rb = request.evaluatePreconditions(EtagCache.get(uriInfo.getPath))
        if (rb != null) throw new WebApplicationException(rb.build())
      }
      case "PUT" => {
        EtagCache.remove(uriInfo.getPath)
      }
    }

  }

}