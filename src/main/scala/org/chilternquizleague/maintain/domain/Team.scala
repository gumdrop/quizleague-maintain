package org.chilternquizleague.maintain.domain

case class Team(
    name:String,
    shortName:String,
    venue:Ref[Venue])