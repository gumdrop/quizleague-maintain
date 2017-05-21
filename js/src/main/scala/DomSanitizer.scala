package angular.platformBrowser

import scala.scalajs.js
import scala.scalajs.js.annotation.JSImport

@js.native
@JSImport("@angular/platform-browser","DomSanitizer")
class DomSanitizer extends js.Object{
  
  def bypassSecurityTrustHtml(value: String) : js.Any = js.native
  def bypassSecurityTrustStyle(value: String) :  js.Any = js.native
  def bypassSecurityTrustScript(value: String) :  js.Any = js.native
  def bypassSecurityTrustUrl(value: String) :  js.Any = js.native
  def bypassSecurityTrustResourceUrl(value: String) :  js.Any = js.native
}