package quizleague.util.json.codecs

import quizleague.domain._
import ScalaTimeCodecs._

object DomainCodecs{
  import io.circe._, io.circe.generic.semiauto._
  implicit val refFixturesDecoder: Decoder[Ref[Fixtures]] = deriveDecoder
  implicit val refFixturesEncoder: Encoder[Ref[Fixtures]] = deriveEncoder
  implicit val refFixtureDecoder: Decoder[Ref[Fixture]] = deriveDecoder
  implicit val refFixtureEncoder: Encoder[Ref[Fixture]] = deriveEncoder
  implicit val refResultsDecoder: Decoder[Ref[Results]] = deriveDecoder
  implicit val refResultsEncoder: Encoder[Ref[Results]] = deriveEncoder
  implicit val refResultDecoder: Decoder[Ref[Result]] = deriveDecoder
  implicit val refResultEncoder: Encoder[Ref[Result]] = deriveEncoder
  implicit val refTextDecoder: Decoder[Ref[Text]] = deriveDecoder
  implicit val refTextEncoder: Encoder[Ref[Text]] = deriveEncoder
  implicit val refLeagueTableDecoder: Decoder[Ref[LeagueTable]] = deriveDecoder
  implicit val refLeagueTableEncoder: Encoder[Ref[LeagueTable]] = deriveEncoder  
  implicit val refCompetitionDecoder: Decoder[Ref[Competition]] = deriveDecoder
  implicit val refCompetitionEncoder: Encoder[Ref[Competition]] = deriveEncoder  
  implicit val refVenueDecoder: Decoder[Ref[Venue]] = deriveDecoder
  implicit val refVenueEncoder: Encoder[Ref[Venue]] = deriveEncoder
  implicit val refTeamDecoder: Decoder[Ref[Team]] = deriveDecoder
  implicit val refTeamEncoder: Encoder[Ref[Team]] = deriveEncoder
  implicit val refUserDecoder: Decoder[Ref[User]] = deriveDecoder
  implicit val refUserEncoder: Encoder[Ref[User]] = deriveEncoder
  implicit val eventDecoder: Decoder[Event] = deriveDecoder
  implicit val eventEncoder: Encoder[Event] = deriveEncoder
//  implicit val venueDecoder: Decoder[Venue] = deriveDecoder
//  implicit val teamDecoder: Decoder[Team] = deriveDecoder
  implicit val fixtureDecoder: Decoder[Fixture] = deriveDecoder
  implicit val resultsDecoder: Decoder[Results] = deriveDecoder
  implicit val reportDecoder: Decoder[Report] = deriveDecoder
  implicit val resultDecoder: Decoder[Result] = deriveDecoder
  
  
}