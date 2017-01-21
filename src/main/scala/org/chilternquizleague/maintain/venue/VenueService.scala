package org.chilternquizleague.maintain.venue

import angulate2.std.Injectable
import angulate2.ext.classModeScala
import angulate2.http.Http
import org.chilternquizleague.maintain.service.EntityService
import org.chilternquizleague.maintain.model.Venue
import org.chilternquizleague.maintain.domain.{Venue => DomVenue}
import rxjs.Observable

@Injectable
@classModeScala
class VenueService(override val http:Http) extends EntityService[Venue] with VenueNames{
  override type U = DomVenue
  
  override protected def mapIn(venue:Venue) = {
    DomVenue(venue.id, venue.name, Option(venue.phone), Option(venue.email), Option(venue.website))
  }
  
  override protected def mapOut(venue:DomVenue):Observable[Venue] = Observable.of(mapOutSparse(venue))
  override protected def mapOutSparse(venue:DomVenue):Venue = {
    Venue(venue.id, venue.name, venue.phone.getOrElse(null), venue.email.getOrElse(null), venue.website.getOrElse(null))
  }
  override protected def make():DomVenue = DomVenue(newId(), "",None,None,None)
  
  import json._
  override def ser(item:DomVenue) = item.js.toJSONString
  override def deser(jsonString:String) = JValue.fromString(jsonString).toObject[DomVenue]

}