package quizleague.web.service.applicationcontext

import scala.scalajs.js

import quizleague.domain.{ ApplicationContext => Dom, EmailAlias => DomEmailAlias }
import quizleague.web.model.{ ApplicationContext, EmailAlias, GlobalText, Season, User }
import quizleague.web.names.ApplicationContextNames
import quizleague.web.service.{ GetService, PutService }
import quizleague.web.service.globaltext.{ GlobalTextGetService, GlobalTextPutService }
import quizleague.web.service.season.{ SeasonGetService, SeasonPutService }
import quizleague.web.service.user.{ UserGetService, UserPutService }
import quizleague.web.util.Logging
import rxscalajs.Observable
import io.circe._,io.circe.parser._,io.circe.syntax._
import quizleague.util.json.codecs.DomainCodecs._
import scala.scalajs.js.JSConverters._

trait ApplicationContextGetService extends GetService[ApplicationContext] with ApplicationContextNames with Logging {
  override type U = Dom

  val globalTextService: GlobalTextGetService
  val userService: UserGetService
  val seasonService: SeasonGetService
  override protected def mapOutSparse(context: Dom) = ApplicationContext(context.id, context.leagueName, refObs(context.textSet, globalTextService), refObs(context.currentSeason, seasonService), context.senderEmail, mapOutAliases(context.emailAliases), context.cloudStoreBucket)
  def mapOutAliases(list: List[DomEmailAlias]) = list.map(e => EmailAlias(e.alias, refObs(e.user, userService))).toJSArray

  def listTextSets() = globalTextService.list()
  
  def get(): Observable[ApplicationContext] = get(Dom.singletonId)

  override protected def dec(json:js.Any) = decodeJson[U](json)
}

trait ApplicationContextPutService extends PutService[ApplicationContext] with ApplicationContextGetService {
  override val globalTextService: GlobalTextPutService
  override val userService: UserPutService
  override val seasonService: SeasonPutService

  override protected def mapIn(context: ApplicationContext) = Dom(context.leagueName, globalTextService.ref(context.textSet), seasonService.ref(context.currentSeason), context.senderEmail, context.emailAliases.map(ea => DomEmailAlias(ea.alias, userService.ref(ea.user))).toList, context.cloudStoreBucket)
  override protected def make() = Dom(newId(), "", null, null, "", List(), "")

  override def enc(item: Dom) = item.asJson
}
