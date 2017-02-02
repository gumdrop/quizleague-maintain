package org.chilternquizleague.util.json.codecs

import org.chilternquizleague.maintain.domain._

object DomainCodecs{
  import io.circe._, io.circe.generic.semiauto._
  implicit val fixturesDecoder: Decoder[Fixtures] = deriveDecoder
  implicit val refFixturesDecoder: Decoder[Ref[Fixtures]] = deriveDecoder
}