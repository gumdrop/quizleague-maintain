package org.chilternquizleague.web.model

import angulate2.std.Data

@Data
case class User(
    id:String,
    name:String,
    email:String,
    retired:Boolean = false
)