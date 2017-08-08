package quizleague.web.site.text

import quizleague.web.service.text._
import quizleague.web.site.ServiceRoot
import angulate2.http._
import angulate2.std._
import angulate2.ext._
import quizleague.web.service.globaltext._
import angulate2.common.CommonModule
import angulate2.platformBrowser.BrowserModule
import angular.material.MaterialModule


@NgModule(
  imports = @@[CommonModule, BrowserModule, MaterialModule],
  declarations = @@[TextComponent,NamedTextComponent],
  providers = @@[TextService,GlobalTextService],  
  exports = @@[TextComponent,NamedTextComponent]
)
class TextModule 



@Injectable
@classModeScala
class TextService(override val http:Http) extends TextGetService with ServiceRoot

@Injectable
@classModeScala
class GlobalTextService(
    override val http:Http, 
    override val textService:TextService
    ) extends GlobalTextGetService with ServiceRoot