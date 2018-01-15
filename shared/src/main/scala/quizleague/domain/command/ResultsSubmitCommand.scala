package quizleague.domain.command

case class ResultsSubmitCommand(fixtures:List[ResultValues], reportText:Option[String], email:String)
case class ResultValues(fixtureId:String, homeScore:Int, awayScore:Int)