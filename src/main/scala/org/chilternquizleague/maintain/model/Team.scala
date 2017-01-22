package org.chilternquizleague.maintain.model

import angulate2.std.Data
import scala.scalajs.js.annotation.JSExport

@Data
case class Team(
    id:String,
    name:String,
    shortName:String,
    venue:Venue,
    users:List[User],
    retired:Boolean = false
)