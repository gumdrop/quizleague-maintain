package org.chilternquizleague.maintain.model

import angulate2.std.Data

@Data
case class Season(
    id:String,
    startYear:String,
    endYear:String,
    text:Text
)