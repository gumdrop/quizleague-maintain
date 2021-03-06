package quizleague.conversions

import java.util.logging.{Level, Logger}

import quizleague.domain.Ref

import scala.reflect.ClassTag
import quizleague.data.Storage
import io.circe.Decoder
import quizleague.domain.Entity

import scala.language.implicitConversions
import scala.collection.mutable._

object RefConversions {

  val log = Logger.getLogger(this.getClass.toString())
  
  class StorageContext(entities:Map[String,Entity]) {
    private[RefConversions] def get[T <: Entity](id:String)(implicit tag:ClassTag[T]):Option[T] = entities.get(tag.runtimeClass.getName + id).asInstanceOf[Option[T]]
    private[RefConversions] def put[T <: Entity](entity:T)(implicit tag:ClassTag[T]):T =  {entities += ((tag.runtimeClass.getName + entity.id, entity)); entity}
  
  }
  
  object StorageContext{
    
    def apply() = new StorageContext(Map())
    def apply(entities:Map[String,Entity]) = new StorageContext(entities)
    
  }
  
  implicit def refToObject[T <: Entity](ref:Ref[T])(implicit tag:ClassTag[T],decoder: Decoder[T], context:StorageContext = StorageContext()):T = context.get(ref.id).getOrElse(context.put(Storage.load(ref.id)))
  implicit def refListToObjectList[T <: Entity](list:List[Ref[T]])(implicit tag:ClassTag[T],decoder: Decoder[T], context:StorageContext = StorageContext()):List[T] = list.flatMap(safeRefToObject(_))
 
  private def safeRefToObject[T <: Entity](ref:Ref[T])(implicit tag:ClassTag[T],decoder: Decoder[T], context:StorageContext = StorageContext()):List[T] = {
    try{
      List(refToObject(ref))
    }
    catch {
      case npe : NullPointerException => {
        log.log(Level.WARNING, s"error in load for ref : $ref", npe)
        List()
      }
    }
  }
}