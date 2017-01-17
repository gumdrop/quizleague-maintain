package org.chilternquizleague.maintain.domain

import json.accessor

@accessor case class Team (
    id:String,
    name:String,
    shortName:String,
    venue:Ref) extends Entity