package org.chilternquizleague.maintain.model

import angulate2.std.Data

@Data
case class Ref[T](typeName:String,id:String)