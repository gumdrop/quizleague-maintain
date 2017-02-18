package quizleague.web.service.applicationcontext

import angulate2.http.Http
import quizleague.web.model._
import quizleague.domain.{ApplicationContext => Dom}
import quizleague.domain.{EmailAlias => DomEmailAlias}

import quizleague.domain.Ref
import rxjs.Observable
import quizleague.web.maintain.component.ComponentNames
import scala.scalajs.js
import quizleague.web.service._
import quizleague.web.service.globaltext._
import quizleague.web.service.user._
import quizleague.web.maintain.applicationcontext.ApplicationContextNames
import quizleague.web.util.Logging



trait ApplicationContextGetService extends GetService[ApplicationContext] with ApplicationContextNames with Logging{
    override type U = Dom
   
    val globalTextService:GlobalTextGetService
    val userService:UserGetService
  override protected def mapOutSparse(context:Dom) = ApplicationContext(context.id, context.leagueName, null, context.senderEmail, js.Array())
  override protected def mapOut(context:Dom) =
    Observable.zip(
        globalTextService.get(context.textSet),
        mapOutAliases(context.emailAliases),
        (textSet:GlobalText, emailAliases:js.Array[EmailAlias]) => log(ApplicationContext(context.id,context.leagueName,textSet,context.senderEmail,emailAliases), "mapOut ApplicationContext"))
  
  def mapOutAliases(list:List[DomEmailAlias]):Observable[js.Array[EmailAlias]] = 
    Observable.zip(list.map((e:DomEmailAlias) => userService.get(e.user).map((u:User,i:Int) => EmailAlias(e.alias, u))):_*)

  def listTextSets() = globalTextService.list()
        
  def get():Observable[ApplicationContext] = list().switchMap((x,i) => get(x(0).id))
  
  import io.circe._, io.circe.generic.auto._, io.circe.parser._, io.circe.syntax._

  override def deser(jsonString:String) = decode[Dom](jsonString).merge.asInstanceOf[Dom]

}

trait ApplicationContextPutService extends PutService[ApplicationContext] with ApplicationContextGetService{
  override val globalTextService:GlobalTextPutService   
  override val userService:UserPutService
  
  override protected def mapIn(context:ApplicationContext) = Dom(context.id, context.leagueName, globalTextService.getRef(context.textSet), context.senderEmail, context.emailAliases.map(ea => DomEmailAlias(ea.alias, userService.getRef(ea.user))).toList)
  override protected def make() = Dom(newId(), "", null, "",List())
 
  import io.circe._, io.circe.generic.auto._, io.circe.parser._, io.circe.syntax._

  override def ser(item:Dom) = item.asJson.noSpaces
 
}
