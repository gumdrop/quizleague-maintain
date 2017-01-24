package org.chilternquizleague.maintain.model

import angulate2.std.Data
import scala.scalajs.js

@Data
case class Team(
    id:String,
    name:String,
    shortName:String,
    venue:Venue,
    text:Text,
    users:js.Array[User],
    retired:Boolean
)