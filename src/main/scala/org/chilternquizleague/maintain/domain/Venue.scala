package org.chilternquizleague.maintain.domain

case class Venue(
    id:String,
    name:String,
    phone:String,
    email:String,
    website:String
) extends Entity