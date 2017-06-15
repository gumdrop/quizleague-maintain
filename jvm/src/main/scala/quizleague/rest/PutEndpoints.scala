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
  
  this:EtagSupport =>

  private def save[T <: Entity](json: String)(implicit tag: ClassTag[T], decoder: Decoder[T], encoder: Encoder[T]) = {
    Storage.save(decode[T](json).merge.asInstanceOf[T])
    val etag = EtagCache.add(uriInfo.getPath)
    Response.status(200).header(HttpHeaders.ETAG, etag).`type`(MediaType.APPLICATION_JSON_TYPE).entity(json).build
  }

  @PUT
  @Path("/venue/{id}")
  def venue_(body: String) = save[Venue](body)

  @PUT
  @Path("/team/{id}")
  def team_(body: String) = save[Team](body)

  @PUT
  @Path("/text/{id}")
  def text_(body: String) = save[Text](body)

  @PUT
  @Path("/season/{id}")
  def season_(body: String) = save[Season](body)

  @PUT
  @Path("/competition/{id}")
  def competition_(body: String) = save[Competition](body)

  @PUT
  @Path("/results/{id}")
  def results_(body: String) = save[Results](body)
  
  @PUT
  @Path("/result/{id}")
  def result_(body: String) = save[Result](body)

  @PUT
  @Path("/fixtures/{id}")
  def fixtures_(body: String) = save[Fixtures](body)
  @PUT
  @Path("/fixture/{id}")
  def fixture_(body: String) = save[Fixture](body)

  @PUT
  @Path("/user/{id}")
  def user_(body: String) = save[User](body)

}