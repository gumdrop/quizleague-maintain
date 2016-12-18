package org.chilternquizleague.maintain.model

import angulate2.std.Data

@Data
case class Team(
    id:String,
    name:String,
    shortName:String,
    venue:Venue)