package quizleague.web.util.validation

import scalajs.js
import js._



object Functions {
  val emailRegex = """^[a-z0-9._%+-]+@[a-z0-9.-]+\.[a-z]{2,}$"""
  import DynamicImplicits.truthValue
  def required(name:String):js.Function1[js.Dynamic,Boolean|String] = v => if(!(!v)) true else s"$name is required"
  def regex(regex:String)(message:String):js.Function1[String,Boolean|String] = in => if(in.toLowerCase.matches(regex)) true else message
  def isEmail(name:String):js.Function1[String,Boolean|String] = regex(emailRegex)(s"$name must be an email address")
}