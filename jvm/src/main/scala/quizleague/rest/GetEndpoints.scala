package quizleague.rest

import javax.ws.rs._
import javax.ws.rs.core._
import javax.ws.rs.core.HttpHeaders._
import io.circe._, io.circe.generic.auto._, io.circe.syntax._
import quizleague.data.Storage
import quizleague.domain._
import quizleague.util.json.codecs.DomainCodecs._
import quizleague.util.json.codecs.ScalaTimeCodecs._
import scala.reflect.ClassTag

trait GetEndpoints {
  protected def out[T <: Entity](id: String)(implicit tag: ClassTag[T], dec: Decoder[T], enc: Encoder[T]):Response = {
    try Response.status(200)
    .entity(Storage.load[T](id).asJson.noSpaces.toString)
    .`type`(MediaType.APPLICATION_JSON)
    .header(CACHE_CONTROL, "max-age=3600")
    .build
    catch { case e: Exception => { e.printStackTrace; Response.status(404).build } }
  }
  
  protected def out[T <: Entity](id: String, etag:String)(implicit tag: ClassTag[T], dec: Decoder[T], enc: Encoder[T]):Response = {
    if(EtagCache.isMatch(id, etag)) Response.status(304).build
    else {
      val r = out(id)
      Response.fromResponse(r)
      .header(ETAG, EtagCache.add(id, r.getEntity))
      .header(HttpHeaders.CACHE_CONTROL, "max-age=60")
      .build
    }
  }

  protected def list[T <: Entity](implicit tag: ClassTag[T], dec: Decoder[T], enc: Encoder[T]) = {
    try Response.status(200).entity(Storage.list[T].asJson.noSpaces.toString).build
    catch { case e: Exception => { e.printStackTrace; Response.status(404).build } }
  }

  @GET
  @Path("/applicationcontext")
  def applicationContext() = list[ApplicationContext]

  @GET
  @Path("/globaltext/{id}")
  def globalText(@PathParam("id") id: String) = out[GlobalText](id)

  @GET
  @Path("/team")
  def team() = list[Team]

  @GET
  @Path("/team/{id}")
  def team(@PathParam("id") id: String) = out[Team](id)

  @GET
  @Path("/text")
  def text() = list[Text]

  @GET
  @Path("/text/{id}")
  def text(@PathParam("id") id: String, @HeaderParam(IF_NONE_MATCH) etag:String) = out[Text](id,etag)

  @GET
  @Path("/season")
  def season() = list[Season]

  @GET
  @Path("/season/{id}")
  def season(@PathParam("id") id: String) = out[Season](id)

  @GET
  @Path("/venue")
  def venue() = list[Venue]

  @GET
  @Path("/venue/{id}")
  def venue(@PathParam("id") id: String) = out[Venue](id)

  @GET
  @Path("/results")
  def results() = list[Results]

  @GET
  @Path("/results/{id}")
  def results(@PathParam("id") id: String, @HeaderParam(IF_NONE_MATCH) etag:String) = out[Results](id, etag)

  @GET
  @Path("/result")
  def result() = list[Result]

  @GET
  @Path("/result/{id}")
  def result(@PathParam("id") id: String) = out[Result](id)

  @GET
  @Path("/fixtures")
  def fixtures() = list[Fixtures]

  @GET
  @Path("/fixtures/{id}")
  def fixtures(@PathParam("id") id: String) = out[Fixtures](id)

  @GET
  @Path("/fixture")
  def fixture() = list[Fixture]

  @GET
  @Path("/fixture/{id}")
  def fixture(@PathParam("id") id: String) = out[Fixture](id)

  @GET
  @Path("/competition")
  def competition() = list[Competition]

  @GET
  @Path("/competition/{id}")
  def competition(@PathParam("id") id: String, @HeaderParam(IF_NONE_MATCH) etag:String) = out[Competition](id,etag)

  @GET
  @Path("/user")
  def user() = list[User]

  @GET
  @Path("/user/{id}")
  def user(@PathParam("id") id: String) = out[User](id)
}