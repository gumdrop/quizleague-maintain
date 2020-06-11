package quizleague.domain.container

import quizleague.domain._

case class DomainContainer(
    
    applicationcontext:List[ApplicationContext],
    competition:List[Competition],
    fixtures:List[Fixtures],
    fixture:List[Fixture],
    globaltext:List[GlobalText],
    leaguetable:List[LeagueTable],
    reports: List[Reports],
    season:List[Season],
    team:List[Team],
    text:List[Text],
    user:List[User],
    venue:List[Venue]   

)

case class NestedDomainContainer(
    applicationcontext:Map[String,ApplicationContext],
    competition:Map[String,Competition],
    fixtures:Map[String,Fixtures],
    fixture:Map[String,Fixture],
    globaltext:Map[String,GlobalText],
    leaguetable:Map[String,LeagueTable],
    reports:Map[String,Report],
    season:Map[String,Season],
    team:Map[String,Team],
    text:Map[String,Text],
    user:Map[String,User],
    venue:Map[String,Venue],
    chat:Map[String, Chat],
    chatMessage: Map[String, ChatMessage]


)