package org.chilternquizleague.maintain.model

import angulate2.std._

@Data
case class Venue(
    id:String,
    name:String,
    phone:String,
    email:String,
    website:String
)
