package quizleague.domain.command

import quizleague.domain.Key

case class ResultsSubmitCommand(fixtures:List[ResultValues], reportText:Option[String], userID:String)
case class ResultValues(fixtureKey:Key, homeScore:Int, awayScore:Int)