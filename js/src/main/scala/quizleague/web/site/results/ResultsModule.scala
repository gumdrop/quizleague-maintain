package quizleague.web.site.results

import angulate2.std._

import scala.scalajs.js
import quizleague.web.model._
import quizleague.domain.{Competition => Dom}
import quizleague.web.names.ComponentNames

import angulate2.ext.classModeScala
import quizleague.web.service.results._
import quizleague.web.site.text.TextService
import quizleague.web.site.team.TeamService
import quizleague.web.site._
import angulate2.http.Http
import quizleague.web.site.user.UserService
import quizleague.web.site.fixtures._


@Injectable
@classModeScala
class ResultsService(override val http:Http, 
  override val resultService: ResultService,
  override val fixturesService: FixturesService
 
) extends ResultsGetService with ServiceRoot{
  
   
}



@Injectable
@classModeScala
class ResultService(override val http:Http, 
val userService:UserService,
  val fixtureService:FixtureService,
  val textService:TextService,
  val teamService:TeamService 
) extends ResultGetService with ServiceRoot{
  
   
}

