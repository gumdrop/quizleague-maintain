package quizleague.web.maintain.competition

import angulate2.std._
import angulate2.router.ActivatedRoute
import angulate2.common.Location
import quizleague.web.maintain.component.ItemComponent
import quizleague.web.maintain.component._
import quizleague.web.model._
import scala.scalajs.js
import angulate2.ext.classModeScala
import TemplateElements._
import quizleague.web.maintain.text.TextService
import angulate2.router.Router
import js.Dynamic.{ global => g }
import angulate2.core.Input
import quizleague.web.maintain.text.TextEditMixin
import quizleague.web.util.Logging
import quizleague.web.maintain.text.TextEditMixin
import angulate2.router.Router
import scala.scalajs.js.annotation.JSExport


trait CompetitionComponent extends ItemComponent[Competition] with TextEditMixin[Competition] with Logging{
  
  @JSExport
  def fixtures(comp:Competition) = {
     service.cache(item)
     router.navigateRelativeTo(route, "fixtures")
   }
   
  @JSExport 
  def results(comp:Competition) = {
     service.cache(item)
     router.navigateRelativeTo(route, "results")
   }
   
  @JSExport 
  def tables(comp:Competition) = {
     service.cache(item)
     router.navigateRelativeTo(route, "leaguetable")
   }
}
    