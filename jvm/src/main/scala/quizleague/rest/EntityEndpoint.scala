package quizleague.rest

import javax.ws.rs._
import javax.ws.rs.core._
import io.circe._, io.circe.generic.auto._,  io.circe.syntax._
import quizleague.data.Storage
import quizleague.domain._
import quizleague.util.json.codecs.DomainCodecs._
import quizleague.util.json.codecs.ScalaTimeCodecs._
import scala.reflect.ClassTag



@Path("/entity")
class EntityEndpoint extends GetEndpoints