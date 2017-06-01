package quizleague.rest

import javax.ws.rs._
import javax.ws.rs.core.Response



@Path("/entity")
class Entity {

	@GET
	@Path("/venue/{id}")
	def venue(@PathParam("id")id:String):Response = {

	  val output = s"venue - $id"

		Response.status(200).entity(output).build();

	}
	
  @GET
	@Path("/user/{id}")
	def user(@PathParam("id")id:String):Response = {

    val output = s"user - $id"  

		Response.status(200).entity(output).build();

	}

}