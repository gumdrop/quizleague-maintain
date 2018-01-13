package quizleague.web.service

import scala.scalajs.js

import io.circe._, io.circe.parser._
import quizleague.web.names.ComponentNames
import quizleague.web.util.Logging._
import quizleague.web.model.Model
import rxscalajs._
import rxscalajs.dom.Request

trait PostService {


  
  protected def command[R,V](pathParts:List[String],i:Option[V])(implicit decoder:Decoder[R],encoder:Encoder[V]):Observable[R] = {
    
    val path = pathParts.mkString("/")
    
    val request = Request(
        path,
        headers = Map("Content-Type" -> "application/json", "Accept-Content" -> "application/json"),
        data = i.fold("")(encoder(_).noSpaces),
        method = "POST")
    
    Observable.ajax(request).map(response => decode[R](response.body).fold(e => {throw e}, u => u))
      .onErrorResumeNext(x => { log(request,s"error in ${request.method} for $path : $x ");null })
  }
  
 }