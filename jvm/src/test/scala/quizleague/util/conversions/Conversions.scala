package quizleague.util.conversions

import java.util.UUID
import scala.language.implicitConversions 

object Conversions {
  
  implicit def uuidToString(uuid:UUID):String = uuid.toString()
  
}