package quizleague.rest

import javax.ws.rs._
import javax.ws.rs.core._
import javax.ws.rs.core.HttpHeaders._
import io.circe._, io.circe.syntax._
import quizleague.data.Storage
import quizleague.domain._
import quizleague.util.json.codecs.DomainCodecs._
import scala.reflect.ClassTag

trait GetEndpoints {
  
  this:EtagSupport =>  
  
  val defaultCacheAge:Int
  val shortCacheAge:Int
  
  protected def out[T <: Entity](id: String, cacheAge:Int = defaultCacheAge)(implicit tag: ClassTag[T], dec: Decoder[T], enc: Encoder[T]):Response = {
    try Response.status(200)
    .entity(Storage.load[T](id).asJson.noSpaces.toString)
    .`type`(MediaType.APPLICATION_JSON)
    .header(CACHE_CONTROL, s"max-age=$cacheAge")
    .header(ETAG, EtagCache.get(uriInfo.getPath))
    .build
    catch { case e: Exception => { e.printStackTrace; Response.status(404).build } }
  }

  protected def list[T <: Entity](implicit tag: ClassTag[T], dec: Decoder[T], enc: Encoder[T]) = listOut(Storage.list[T])
  
  protected def listOut[T <: Entity](list:List[T])(implicit enc: Encoder[T]) = {
    try Response.status(200)
    .`type`(MediaType.APPLICATION_JSON)
    .entity(list.asJson.noSpaces.toString)
    .header(CACHE_CONTROL, s"max-age=$defaultCacheAge")
    .build
    catch { case e: Exception => { e.printStackTrace; Response.status(404).build } }
  }

  @GET
  @Path("/applicationcontext")
  def applicationContext() = list[ApplicationContext]
  
  @GET
  @Path("/applicationcontext/{id}")
  def applicationContext(@PathParam("id") id: String) = out[ApplicationContext](id)

  @GET
  @Path("/globaltext")
  def globalText() = list[GlobalText]
  
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
  def text(@PathParam("id") id: String) = out[Text](id,shortCacheAge)

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
  def results(@PathParam("id") id: String) = out[Results](id, shortCacheAge)

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
  def competition(@PathParam("id") id: String) = out[Competition](id,shortCacheAge)

  @GET
  @Path("/user")
  def user() = list[User]

  @GET
  @Path("/user/{id}")
  def user(@PathParam("id") id: String) = out[User](id)
  
  @GET
  @Path("/leaguetable")
  def leaguetable() = list[LeagueTable]

  @GET
  @Path("/leaguetable/{id}")
  def leaguetable(@PathParam("id") id: String) = out[LeagueTable](id)
}