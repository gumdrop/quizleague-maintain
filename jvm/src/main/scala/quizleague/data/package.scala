import quizleague.domain.ApplicationContext
import quizleague.util.json.codecs.DomainCodecs._

package quizleague{

  package object data {
      def applicationContext() = Storage.load[ApplicationContext](ApplicationContext.singletonId)
  }
}