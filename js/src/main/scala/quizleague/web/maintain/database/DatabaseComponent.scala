package quizleague.web.maintain.database

import scalajs.js
import quizleague.web.core._
import org.scalajs.dom
import org.scalajs.dom.raw.HTMLInputElement
import quizleague.web.util.Logging._
import com.felstar.scalajs.vue.VueRxComponent


@js.native
trait DatabaseComponent extends VueRxComponent{
  var uploadComplete:Boolean
  var uploadText:String
}

object DatabaseComponent extends RouteComponent{
  
  type facade = DatabaseComponent
  
  val template = """
    <v-container grid-list-lg>
      <v-layout column fluid>
       <div>
        <input type="file" id="upload-text-field" label="Database dump file" >
        <v-btn flat color="primary" v-on:click="upload"><v-icon left>file_upload</v-icon>Upload</v-btn> 
        <v-dialog v-model="uploadComplete">
          <v-card>
            <v-card-text>{{uploadText}}</v-card-text>
          </v-card>
        </v-dialog>
        </div>
      </v-layout>
    </v-container>
    """
  
    def upload(c:facade){
      val file = dom.document.getElementById("upload-text-field").asInstanceOf[HTMLInputElement].files(0)
      
      DatabaseService.upload(file).subscribe(x => {c.uploadText="Success!";c.uploadComplete = true},e => {c.uploadText=e.toString;c.uploadComplete=true})
    
  }
  
  data("uploadComplete",false)
  data("uploadText","Not yet uploaded")
  method("upload")({upload _}:js.ThisFunction)
  
}