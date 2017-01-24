package org.chilternquizleague.maintain.domain

case class Text(
    id:String,
    text:String,
    retired:Boolean = false
) extends Entity