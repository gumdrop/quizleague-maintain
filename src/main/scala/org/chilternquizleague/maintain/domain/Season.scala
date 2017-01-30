package org.chilternquizleague.maintain.domain

case class Season(
    id:String,
    retired:Boolean = false
) extends Entity