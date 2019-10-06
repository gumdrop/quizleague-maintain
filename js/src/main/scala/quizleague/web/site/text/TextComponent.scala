package quizleague.web.site.text

import quizleague.web.core._
import quizleague.web.model.Text

import scala.scalajs.js


@js.native
trait This extends IdComponent{
  val text:Text = js.native
}

object TextComponent extends Component {
  
  type facade = This
  
  override val name = "ql-text"
    
    override val template =  """
          <div>
          <v-skeleton-loader
          v-if="!text"
          type="paragraph"
        ></v-skeleton-loader>
          <div v-else>
            <div v-if="text.mimeType=='text/html'" v-html="text.text"></div>
            <div v-if="text.mimeType=='text/plain'" v-text="text.text"></div>
            <ql-markdown v-if="text.mimeType=='text/markdown'" :text="text.text"></ql-markdown>
          </div>
          </div>
         """
     props("id")
     subscription("text","id")(v => TextService.get(v.id))

}


