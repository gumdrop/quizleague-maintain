package quizleague.domain

trait Entity extends Serializable  {
  type U = this.type

  val id:String
  val retired:Boolean
  var key:Option[Key] = None

  def withKey(key:Key):U = {
    this.key = Option(key)
    this
  }

  def withKey(key:Option[Key]):U = {
    this.key = key
    this
  }

  def parentKey = key.flatMap(_.parentKey)
}

