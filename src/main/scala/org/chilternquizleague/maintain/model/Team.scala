package org.chilternquizleague.maintain.model

import org.chilternquizleague.maintain.domain.Ref
import angulate2.std.Data

@Data
case class Team(
    name:String,
    shortName:String)