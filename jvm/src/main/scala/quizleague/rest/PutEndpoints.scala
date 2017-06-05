package quizleague.rest

import javax.ws.rs._
import javax.ws.rs.core._
  import io.circe._, io.circe.generic.auto._, io.circe.parser._, io.circe.syntax._
import quizleague.data.Storage
import quizleague.domain._
import quizleague.util.json.codecs.DomainCodecs._
import quizleague.util.json.codecs.ScalaTimeCodecs._
import scala.reflect.ClassTag

trait PutEndpoints {
  
  private def save[T <: Entity](json:String)(implicit tag: ClassTag[T],decoder: Decoder[T], encoder: Encoder[T]) = {
    Storage.save(decode[T](json).merge.asInstanceOf[T])
    Response.status(201).build
  }
  
  
  @PUT
	@Path("/venue/{id}")
	@Consumes(Array(MediaType.APPLICATION_JSON))
	def venue(body:String) = save[Venue](body)
}