package org.chilternquizleague.maintain.model

import angulate2.std.Data

@Data
case class Text(
    id:String,
    text:String,
    mimeType:String
)