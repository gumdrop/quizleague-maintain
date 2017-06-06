package quizleague.rest

import javax.ws.rs.Path
import javax.ws.rs.core.Request
import javax.ws.rs.core.Context
import javax.ws.rs.core.HttpHeaders

@Path("/entity")
class EntityEndpoint(
  @Context request:Request,
  @Context headers:HttpHeaders
) extends GetEndpoints with PutEndpoints with PostEndpoints