package org.chilternquizleague.maintain.domain

import json.accessor

@accessor case class Venue(
    id:String,
    name:String,
    phone:Option[String],
    email:Option[String],
    website:Option[String]
) extends Entity