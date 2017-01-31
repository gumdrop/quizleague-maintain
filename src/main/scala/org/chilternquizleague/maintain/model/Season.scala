package org.chilternquizleague.maintain.model

import angulate2.std.Data

@Data
case class Season(
    id:String,
    startYear:Int,
    endYear:Int,
    text:Text
)