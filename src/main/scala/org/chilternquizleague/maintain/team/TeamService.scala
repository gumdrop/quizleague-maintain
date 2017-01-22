package org.chilternquizleague.maintain.team


import angulate2.std.Injectable
import angulate2.ext.classModeScala
import angulate2.http.Http
import org.chilternquizleague.maintain.service.EntityService
import org.chilternquizleague.maintain.model._
import org.chilternquizleague.maintain.domain.{Team => DomTeam}
import org.chilternquizleague.maintain.venue.VenueService
import org.chilternquizleague.maintain.domain.Ref
import rxjs.Observable
import org.chilternquizleague.maintain.component.ComponentNames


@Injectable
@classModeScala
class TeamService(override val http:Http, venueService:VenueService) extends EntityService[Team] with TeamNames{

  override type U = DomTeam
   
  override protected def mapIn(team:Team) = DomTeam(team.id, team.name, team.shortName, venueService.getRef(team.venue))
  override protected def mapOutSparse(team:DomTeam) = Team(team.id, team.name, team.shortName, null)
  override protected def make() = DomTeam(newId(), "", "", null)
  override protected def mapOut(team:DomTeam) = {
    Observable.zip(
        Observable.of(team),
        venueService.get(team.venue),
        (team:DomTeam,venue:Venue) => log(Team(team.id,team.name,team.shortName,venue)))

  }
  
  import io.circe._, io.circe.generic.auto._, io.circe.parser._, io.circe.syntax._

  override def ser(item:DomTeam) = item.asJson.noSpaces
  override def deser(jsonString:String) = decode[DomTeam](jsonString).merge.asInstanceOf[DomTeam]
  
  def listVenues() = venueService.list()
}

