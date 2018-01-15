package quizleague.rest.endpoint

import javax.ws.rs.core._
import javax.ws.rs._
import quizleague.data.Storage
import quizleague.util.json.codecs.DomainCodecs._
import quizleague.conversions.RefConversions._
import quizleague.domain._
import quizleague.rest._
import quizleague.util.collection._
import org.threeten.bp.LocalDate


@Path("/site")
class SiteEndpoint extends SitePostEndpoints{
  
  implicit val context = StorageContext()
  
}