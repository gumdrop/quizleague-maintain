package quizleague.domain.container

import quizleague.domain._
import quizleague.domain.stats.CompetitionStatistics

case class NestedDomainContainer(
    applicationcontext:Map[String,ApplicationContext],
    competition:Map[String,Competition],
    fixtures:Map[String,Fixtures],
    fixture:Map[String,Fixture],
    globaltext:Map[String,GlobalText],
    leaguetable:Map[String,LeagueTable],
    report:Map[String,Report],
    season:Map[String,Season],
    team:Map[String,Team],
    text:Map[String,Text],
    user:Map[String,User],
    venue:Map[String,Venue],
    chat:Map[String, Chat],
    chatMessage: Map[String, ChatMessage],
    competitionStatistics: Map[String,CompetitionStatistics]


)
