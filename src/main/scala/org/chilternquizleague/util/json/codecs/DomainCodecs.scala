package org.chilternquizleague.util.json.codecs

import org.chilternquizleague.maintain.domain._

object DomainCodecs{
  import io.circe._, io.circe.generic.semiauto._
  implicit val refFixturesDecoder: Decoder[Ref[Fixtures]] = deriveDecoder
  implicit val refFixturesEncoder: Encoder[Ref[Fixtures]] = deriveEncoder
  implicit val refResultsDecoder: Decoder[Ref[Results]] = deriveDecoder
  implicit val refResultsEncoder: Encoder[Ref[Results]] = deriveEncoder
  implicit val refTextDecoder: Decoder[Ref[Text]] = deriveDecoder
  implicit val refTextEncoder: Encoder[Ref[Text]] = deriveEncoder
  implicit val refLeagueTableDecoder: Decoder[Ref[LeagueTable]] = deriveDecoder
  implicit val refLeagueTableEncoder: Encoder[Ref[LeagueTable]] = deriveEncoder  
}