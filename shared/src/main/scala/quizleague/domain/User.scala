package quizleague.domain

import java.time.LocalDate

import quizleague.domain.util.CollectionRef

case class User(
    id:String,
    name:String,
    email:String,
    teams:CollectionRef[TeamTenure] = CollectionRef[TeamTenure](),
    retired:Boolean = false
) extends Entity

case class TeamTenure( id:String, team:Ref[Team], start:LocalDate, retired:Boolean = false) extends Entity