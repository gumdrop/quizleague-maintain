package org.chilternquizleague.maintain.domain

import json.accessor

@accessor case class Venue(
    id:String,
    name:String,
    phone:String,
    email:String,
    website:String
) extends Entity