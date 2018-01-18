package quizleague.web.site.common
import quizleague.web.core._
import org.scalajs.dom

object CommonModule extends Module{
  override val components = @@(TitleComponent)
}

object TitleService {
  def title = dom.document.title
  def title_=(text:String){dom.document.title = text}
}