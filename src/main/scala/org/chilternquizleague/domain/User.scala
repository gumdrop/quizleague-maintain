package org.chilternquizleague.domain

case class User(
    id:String,
    name:String,
    email:String,
    retired:Boolean = false
) extends Entity