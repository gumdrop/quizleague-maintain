package quizleague.domain

case class Ref[T <: Entity](typeName:String,id:String, key:Option[Key] = None){
  def getKey():Key = key.getOrElse(Key(None,typeName,id))
}

object Ref{
  def apply[T <: Entity](key: Key): Ref[T] = new Ref(key.entityName, key.id, Option(key))
}