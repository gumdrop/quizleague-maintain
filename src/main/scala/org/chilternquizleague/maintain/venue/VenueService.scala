package org.chilternquizleague.maintain.venue

import angulate2.std.Injectable
import angulate2.ext.classModeScala
import angulate2.http.Http
import org.chilternquizleague.web.service.EntityService
import org.chilternquizleague.web.model.Venue
import org.chilternquizleague.domain.{Venue => DomVenue}
import rxjs.Observable

@Injectable
@classModeScala
class VenueService(override val http:Http) extends EntityService[Venue] with VenueNames{
  override type U = DomVenue
  
  override protected def mapIn(venue:Venue) = {
    DomVenue(venue.id, venue.name, Option(venue.phone), Option(venue.email), Option(venue.website), Option(venue.imageURL), venue.retired)
  }
  
  override protected def mapOut(venue:DomVenue):Observable[Venue] = Observable.of(mapOutSparse(venue))
  override protected def mapOutSparse(venue:DomVenue):Venue = {
    Venue(venue.id, venue.name, venue.phone.getOrElse(null), venue.email.getOrElse(null), venue.website.getOrElse(null), venue.imageURL.getOrElse(null), venue.retired)
  }
  override protected def make():DomVenue = DomVenue(newId(), "",None,None,None,None)
  
  import io.circe._, io.circe.generic.auto._, io.circe.parser._, io.circe.syntax._
  override def ser(item:DomVenue) = item.asJson.noSpaces
  override def deser(jsonString:String) = decode[DomVenue](jsonString).merge.asInstanceOf[DomVenue]

}