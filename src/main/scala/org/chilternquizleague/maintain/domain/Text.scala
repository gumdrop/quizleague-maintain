package org.chilternquizleague.maintain.domain

case class Text(
    id:String,
    text:String,
    mimeType:String,
    retired:Boolean = false
) extends Entity