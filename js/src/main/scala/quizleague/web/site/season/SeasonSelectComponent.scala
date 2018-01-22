package quizleague.web.site.season

import quizleague.web.core._
import quizleague.web.model.Season
import scalajs.js
import quizleague.util.collection._
import com.felstar.scalajs.vue.VueRxComponent
import rxscalajs.Subject
import js.UndefOr._

@js.native
trait SeasonSelectComponent extends VueRxComponent{
  val season:Subject[Season]
  val seasonId:String
}

object SeasonSelectComponent extends Component with SeasonFormatComponent{
  type facade = SeasonSelectComponent
  val name = "ql-season-select"
  val template = """
   <h2 v-if="seasons && seasonId" >
  
    <v-select style="top:5px;"
    :items="wrap(sort(seasons))"
    v-model="seasonId"
    >
    </v-select>
  </h2>
"""
  
  prop("season")
  subscription("seasons")(c => SeasonService.list()) 
  subscription("seasonId")(_.season.map(_.id))
  
  method("sort")((seasons:js.Array[Season]) => seasons.sortBy(_.startYear)(Desc))
  method("wrap")((seasons:js.Array[Season]) => seasons.map(s => new SelectWrapper(formatSeason(s), s.id )))    
  
  watch("seasonId")((c:facade, newValue:js.Any) => {
    val x:js.UndefOr[js.Any] = newValue
    if(x.isDefined) SeasonService.get(c.seasonId).subscribe(s => c.season.next(s))})
  data("seasonId","")
  
  
}

class SelectWrapper(val text:String, val value:js.Any) extends js.Object