package org.chilternquizleague.web.util.json.codecs

import io.circe._
import cats.syntax.either._
import java.time._

object ScalaTimeCodecs {



  implicit val encodeLocalDate: Encoder[LocalDate] = Encoder.encodeString.contramap[LocalDate](_.toString)

  implicit val decodeLocalDate: Decoder[LocalDate] = Decoder.decodeString.emap { str =>
    Either.catchNonFatal(LocalDate.parse(str)).leftMap(t => "LocaDate")
  }

  implicit val encodeYear: Encoder[Year] = Encoder.encodeString.contramap[Year](_.toString)

  implicit val decodeYear: Decoder[Year] = Decoder.decodeString.emap { str =>
    Either.catchNonFatal(Year.parse(str)).leftMap(t => "Year")
  }
  
  implicit val encodeLocalTime: Encoder[LocalTime] = Encoder.encodeString.contramap[LocalTime](_.toString)

  implicit val decodeLocalTime: Decoder[LocalTime] = Decoder.decodeString.emap { str =>
    Either.catchNonFatal(LocalTime.parse(str)).leftMap(t => "LocalTime")
  }
  
  implicit val encodeDuration: Encoder[Duration] = Encoder.encodeString.contramap[Duration](_.toString)

  implicit val decodeDuration: Decoder[Duration] = Decoder.decodeString.emap { str =>
    Either.catchNonFatal(Duration.parse(str)).leftMap(t => "Duration")
  }


}