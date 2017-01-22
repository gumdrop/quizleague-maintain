package org.chilternquizleague.maintain.model

case class User(
    id:String,
    name:String,
    email:String,
    retired:Boolean = false
)