package quizleague.web.service.venue

import quizleague.domain.{ Venue => DomVenue }
import quizleague.web.model.Venue
import quizleague.web.names.VenueNames
import quizleague.web.service.{ GetService, PutService }
import rxjs.Observable
import io.circe._,  io.circe.generic.auto._,io.circe.parser._



trait VenueGetService extends GetService[Venue] with VenueNames {
  override type U = DomVenue
  override protected def mapOutSparse(venue: DomVenue): Venue = {
    Venue(venue.id, venue.name, venue.address, venue.phone.getOrElse(null), venue.email.getOrElse(null), venue.website.getOrElse(null), venue.imageURL.getOrElse(null), venue.retired)
  }
  
  protected def dec(json:String) = decode[U](json)
  protected def decList(json:String) = decode[List[U]](json)

}

trait VenuePutService extends PutService[Venue] with VenueGetService {

  override protected def make(): DomVenue = DomVenue(newId(), "", "",None, None, None, None)

  override protected def mapIn(venue: Venue) = {
    DomVenue(venue.id, venue.name, venue.address, Option(venue.phone), Option(venue.email), Option(venue.website), Option(venue.imageURL), venue.retired)
  }
  import io.circe._, io.circe.generic.auto._, io.circe.parser._, io.circe.syntax._
  override def ser(item: DomVenue) = item.asJson.noSpaces

}