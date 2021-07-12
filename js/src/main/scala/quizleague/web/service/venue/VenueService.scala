package quizleague.web.service.venue

import quizleague.domain.{ Venue => Dom }
import quizleague.web.model.Venue
import quizleague.web.names.VenueNames
import quizleague.web.service.{ GetService, PutService }
import rxscalajs.Observable
import io.circe.parser._,io.circe.syntax._
import quizleague.util.json.codecs.DomainCodecs._
import scalajs.js



trait VenueGetService extends GetService[Venue] with VenueNames {
  override type U = Dom
  override protected def mapOutSparse(venue: Dom): Venue = {
    Venue(venue.id, venue.name, venue.address, venue.phone.getOrElse(null), venue.email.getOrElse(null), venue.website.getOrElse(null), venue.imageURL.getOrElse(null), venue.retired)
  }
  
  protected def dec(json:js.Any) = decodeJson[U](json)

}

trait VenuePutService extends PutService[Venue] with VenueGetService {

  override protected def make(): Dom = withKey(Dom(newId(), "", "",None, None, None, None), null)

  override protected def mapIn(venue: Venue) = {
    Dom(venue.id, venue.name, venue.address, Option(venue.phone), Option(venue.email), Option(venue.website), Option(venue.imageURL), venue.retired)
  }
  override def enc(item: Dom) = item.asJson

}