package quizleague.domain


trait Entity extends Serializable {

  val id:String
  val retired:Boolean
  var key:Option[Key] = None

  def withKey(key:Key):this.type = {
    this.key = Option(key)
    this
  }


  def withKey(key:Option[Key]):this.type = {
    this.key = key
    this
  }

  def parentKey = key.flatMap(_.parentKey)
}

