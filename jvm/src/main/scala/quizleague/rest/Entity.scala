package quizleague.rest

import javax.ws.rs._
import javax.ws.rs.core.Response
import io.circe._, io.circe.generic.auto._,  io.circe.syntax._
import quizleague.data.Storage
import quizleague.domain._
import quizleague.util.json.codecs.DomainCodecs._
import quizleague.util.json.codecs.ScalaTimeCodecs._
import scala.reflect.ClassTag



@Path("/entity")
class EntityEndpoint {

	protected def out[T <: Entity](id:String)(implicit tag:ClassTag[T],dec:Decoder[T], enc:Encoder[T]) = {
	  try Response.status(200).entity(Storage.load[T](id).asJson.noSpaces.toString).build
	  catch{case e:Exception => Response.status(404).build}
	}
	
	protected def list[T <: Entity](implicit tag:ClassTag[T],dec:Decoder[T], enc:Encoder[T]) = {
	  try Response.status(200).entity(Storage.list[T].asJson.noSpaces.toString).build
	  catch{case e:Exception => Response.status(404).build}
	}
  
  @GET
	@Path("/team")
	def team() = list[Team]
	
	@GET
	@Path("/team/{id}")
	def team(@PathParam("id")id:String) = out[Team](id)
	
	@GET
	@Path("/text")
	def text() = list[Text]
	
	@GET
	@Path("/text/{id}")
	def text(@PathParam("id")id:String) = out[Text](id)
	
	@GET
	@Path("/season")
	def season() = list[Season]
	
	@GET
	@Path("/season/{id}")
	def season(@PathParam("id")id:String) = out[Season](id)
	
	@GET
	@Path("/venue")
	def venue() = list[Venue]
	
	@GET
	@Path("/venue/{id}")
	def venue(@PathParam("id")id:String) = out[Venue](id)
	
	@GET
	@Path("/competition")
	def competition() = list[Competition]
	
	@GET
	@Path("/competition/{id}")
	def competition(@PathParam("id")id:String) = out[Competition](id)
	
  @GET
	@Path("/user/{id}")
	def user(@PathParam("id")id:String) = out[User](id)
	
	@GET
	@Path("/user")
	def user() = list[User]

}