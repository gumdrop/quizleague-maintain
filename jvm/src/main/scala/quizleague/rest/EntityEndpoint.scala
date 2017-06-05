package quizleague.rest

import javax.ws.rs.Path

@Path("/entity")
class EntityEndpoint extends GetEndpoints with PutEndpoints with PostEndpoints