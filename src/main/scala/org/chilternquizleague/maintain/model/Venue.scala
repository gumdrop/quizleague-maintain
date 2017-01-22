package org.chilternquizleague.maintain.model

import angulate2.std._
import scala.scalajs.js.annotation.JSExport

@Data
case class Venue(
    id:String,
    name:String,
    phone:String,
    email:String,
    website:String,
    retired:Boolean = false
)

