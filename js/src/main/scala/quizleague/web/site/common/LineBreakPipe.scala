package quizleague.web.site.common

import angulate2.core.Pipe
import angulate2.core.PipeTransform0


@Pipe("lineBreaks")
class LineBreakPipe extends PipeTransform0[String,String]{
  
  def transform(text:String):String = text.replaceAll("\n", "<br>")
  
  
}