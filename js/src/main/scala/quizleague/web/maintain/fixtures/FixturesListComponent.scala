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
      <v-list>
      <v-list-item v-for="fixture in fs" :key="fixture.id">
        <v-list-item-action>
        <v-btn :to="'fixtures/' + fixture.id" text left>{{fixture.date | date("d MMMM yyyy")}}</v-btn>
        </v-list-item-action>
      </v-list-item>
      </v-list>
    </v-layout>
    $addFAB
    $backFAB
  </v-container>
  """    
  
  def add(c:facade):Unit = {
    val fixs = FixturesService.instance(c.item, c.fs)
    FixturesService.save(fixs).subscribe(x => c.$router.push(s"fixtures/${fixs.id}"))

  }
  
 subscription("fs")(c => item(c).flatMap(_.fixtures).map(_.sortBy(_.date)))
  
 method("add")({add _}:js.ThisFunction)

    
    


}
    