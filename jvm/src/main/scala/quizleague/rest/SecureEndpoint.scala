package quizleague.rest

import javax.ws.rs.Path

@Path("/secure")
class SecureEndpoint extends GetEndpoints with PutEndpoints