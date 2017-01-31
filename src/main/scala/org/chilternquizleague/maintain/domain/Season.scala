package org.chilternquizleague.maintain.domain

case class Season(
    id:String,
    startYear:Int,
    endYear:Int,
    text:Ref[Text],
    retired:Boolean = false
) extends Entity