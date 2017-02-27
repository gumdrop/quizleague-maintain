package quizleague.web.model

case class Results(
    id:String, 
    fixtures:Fixtures,
    results:List[Result])
    
case class Result(
    id:String, 
    fixture:Fixture,
    homeScore:Int,
    awayScore:Int,
    submitter:User,
    reports:List[Report])
    
case class Report(
    team:Team,
    text:Text)
    