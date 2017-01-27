package org.chilternquizleague.maintain.model

import angulate2.std.Data

@Data
case class GlobalText(id:String, name:String, text:Map[String,Ref], retired:Boolean=false) extends Entity