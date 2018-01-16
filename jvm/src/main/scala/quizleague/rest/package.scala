package quizleague {

  package object rest {

    import io.circe._, io.circe.parser._

    def deser[T](body: String)(implicit decoder: Decoder[T]): T = decode[T](body).fold(throw _, identity)
  }
}