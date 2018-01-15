package quizleague.domain.command

case class TeamEmailCommand(sender:String, text:String, teamId:String)