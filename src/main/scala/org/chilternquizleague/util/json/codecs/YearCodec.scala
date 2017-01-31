package org.chilternquizleague.util.json.codecs

import io.circe._
import cats.syntax.either._

object YearCodec {

  import java.time.Year

  implicit val encodeYear: Encoder[Year] = Encoder.encodeString.contramap[Year](_.toString)

  implicit val decodeYear: Decoder[Year] = Decoder.decodeString.emap { str =>
    Either.catchNonFatal(Year.parse(str)).leftMap(t => "Instant")
  }
}