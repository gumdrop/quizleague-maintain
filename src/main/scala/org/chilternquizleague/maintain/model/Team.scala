package org.chilternquizleague.maintain.model

import angulate2.std.Data
import scala.scalajs.js

@Data
case class Team(
    id:String,
    name:String,
    shortName:String,
    venue:Venue,
    users:js.Array[User],
    retired:Boolean = false
)