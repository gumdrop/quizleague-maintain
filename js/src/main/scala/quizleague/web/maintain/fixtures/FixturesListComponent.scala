package quizleague.web.maintain.fixtures


import quizleague.web.maintain.component._
import quizleague.web.model._
import scala.scalajs.js
import js.JSConverters._
import TemplateElements._
import quizleague.web.maintain.text.TextService
import js.Dynamic.{ global => g }
import quizleague.web.util.Logging._
import quizleague.web.util.rx._
import quizleague.web.maintain.competition.CompetitionService
import quizleague.web.names.FixturesNames
import quizleague.web.maintain.competition.CompetitionComponentConfig
import quizleague.web.maintain.competition.CompetitionComponent
import quizleague.web.maintain.component.ItemComponentConfig._

@js.native
trait FixturesListComponent extends CompetitionComponent{
  val fs:js.Array[Fixtures]
}

object FixturesListComponent extends CompetitionComponentConfig with FixturesNames{
  
  override type facade = FixturesListComponent
  
  val template = s"""
  <v-container>
    <v-layout column v-if="item ">
      <h2>Fixtures List for {{item.name}} </h2>
      <div v-for="fixture in fs" :key="fixture.id">
        <v-btn :to="'fixtures/' + fixture.id" flat left>{{fixture.date | date("d MMMM yyyy")}}</v-btn>
      </div>
    </v-layout>
    $addFAB
    $backFAB
  </v-container>
  """    
  
  def add(c:facade):Unit = {
    val fixs = FixturesService.instance(c.item, c.fs)
    c.item.fixtures +++= (fixs.id, fixs)
    service.cache(c.item)
    c.$router.push(s"fixtures/${fixs.id}")
  }
  
 subscription("fs")(c => FixturesService.fixturesForCompetition(c.$route.params("id").toString).map(_.sortBy(_.date)))
  
 method("add")({add _}:js.ThisFunction)

    
    


}
    