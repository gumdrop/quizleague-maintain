package org.chilternquizleague.maintain.applicationcontext


import angulate2.std.Injectable
import angulate2.ext.classModeScala
import angulate2.http.Http
import org.chilternquizleague.maintain.service.EntityService
import org.chilternquizleague.maintain.model._
import org.chilternquizleague.maintain.domain.{ApplicationContext => Dom}
import org.chilternquizleague.maintain.domain.{EmailAlias => DomEmailAlias}
import org.chilternquizleague.maintain.venue.VenueService
import org.chilternquizleague.maintain.domain.Ref
import rxjs.Observable
import org.chilternquizleague.maintain.component.ComponentNames
import org.chilternquizleague.maintain.user.UserService
import scala.scalajs.js
import org.chilternquizleague.maintain.text.TextService
import org.chilternquizleague.maintain.globaltext.GlobalTextService
import org.chilternquizleague.util.Logging


@Injectable
@classModeScala
class ApplicationContextService(override val http:Http, userService:UserService, globalTextService:GlobalTextService) extends EntityService[ApplicationContext] with ApplicationContextNames with Logging{

  override type U = Dom
   
  override protected def mapIn(context:ApplicationContext) = Dom(context.id, context.leagueName, globalTextService.getRef(context.textSet), context.senderEmail, context.emailAliases.map(ea => DomEmailAlias(ea.alias, userService.getRef(ea.user))).toList)
  override protected def mapOutSparse(context:Dom) = ApplicationContext(context.id, context.leagueName, null, context.senderEmail, js.Array())
  override protected def make() = Dom(newId(), "", null, "",List())
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

  override def ser(item:Dom) = item.asJson.noSpaces
  override def deser(jsonString:String) = decode[Dom](jsonString).merge.asInstanceOf[Dom]

}
