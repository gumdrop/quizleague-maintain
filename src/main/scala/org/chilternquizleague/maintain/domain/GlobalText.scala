package org.chilternquizleague.maintain.domain

case class GlobalText(id:String, name:String, text:Map[String,Ref[Text]], retired:Boolean=false) extends Entity