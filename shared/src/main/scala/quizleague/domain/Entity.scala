package quizleague.domain

trait Entity extends Serializable  {
  type U = this.type

  val id:String
  val retired:Boolean
  var parentKey:Option[String] = None

  def withParentKey(key:String):U = {
    this.parentKey = Option(key)
    this
  }

}

