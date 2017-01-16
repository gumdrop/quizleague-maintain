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
  val i = instance()
  save(Venue(i.id, "wibble", null, null, null))
  
  override protected def mapIn(venue:Venue) = {
    DomVenue(venue.id, venue.name, venue.phone, venue.email, venue.website)
  }
  
  override protected def mapOut(venue:DomVenue):Observable[Venue] = Observable.of(mapOutSparse(venue))
  override protected def mapOutSparse(venue:DomVenue):Venue = {
    Venue(venue.id, venue.name, venue.phone, venue.email, venue.website)
  }
  override protected def make():DomVenue = DomVenue(newId(), null,null,null,null)
  
  override def toJson(venue:DomVenue) = {
    import io.circe._, io.circe.generic.auto._, io.circe.parser._, io.circe.syntax._

    if(venue != null) venue.asJson.noSpaces else null
  }
  
  override def fromJson(json:String):DomVenue = {
    import io.circe._, io.circe.generic.auto._, io.circe.parser._, io.circe.syntax._

    if(json == null) return null
    
    decode[DomVenue](json) match {
      case Right(x) => x
      case Left(x) => throw x
    }
  }
}