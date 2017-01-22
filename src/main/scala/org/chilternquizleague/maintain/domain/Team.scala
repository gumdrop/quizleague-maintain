package org.chilternquizleague.maintain.domain

case class Team (
    id:String,
    name:String,
    shortName:String,
    venue:Ref[Venue]) extends Entity