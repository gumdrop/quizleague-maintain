package quizleague.data


import com.google.auth.oauth2.GoogleCredentials
import quizleague.domain.Entity
import io.circe._
import reflect._
import scala.collection.JavaConversions._
import scala.collection.JavaConverters._
import java.util.ArrayList
import java.util.Collections
import java.util.Date
import com.google.cloud.firestore.FirestoreOptions
import com.google.auth.Credentials
import java.util.logging.Logger

object Storage {
  
  val log = Logger.getLogger(this.getClass.toString())
  
  val options = FirestoreOptions.getDefaultInstance.toBuilder()
  .setProjectId("ql-firestore-trial").build


  lazy val datastore = options.getService

  
  def save[T <: Entity](entity: T)(implicit tag: ClassTag[T], encoder: Encoder[T]): Unit = {
    val kind = makeKind
    save(kind, entity.id, encoder(entity))
  }

  def load[T <: Entity](id: String)(implicit tag: ClassTag[T], decoder: Decoder[T]): T = load(makeKind, id, decoder)

  def list[T <: Entity](implicit tag: ClassTag[T], decoder: Decoder[T]): List[T] = {
//    val q: Query = new Query(makeKind)
//
//    datastore.prepare(q).asIterable().map(entityToObj(_, decoder)).toList
    List()
  }

  private def makeKind(implicit tag: ClassTag[_]) = tag.runtimeClass.getSimpleName.toLowerCase

  private def save(kind: String, id: String, json: Json): Unit = {

    def handleField(name: String, json: Json) =  ((name, convertObject(json)))

    def convertObject(json: Json): Any = {
      val value: Option[Any] = if (json.isNumber) json.asNumber.map(_.toDouble)
      else if (json.isString) json.asString.map(_.toString())
      else if (json.isBoolean) json.asBoolean
      else if (json.isObject) json.asObject.map(doit(Map()))
      else if (json.isArray) json.asArray.map(v => asArrayList(v.map(o => convertObject(o))))
      else if (json.isNull) None
      else None

      value.getOrElse(null)
    }

    def asArrayList[T](v: Vector[T]) = v.toList.asJava

    def doit(entity: Map[String,Object])(obj: JsonObject) = {

      val res = entity ++ obj.toVector.map((handleField _).tupled)
      
      log.warning(s"entity : $res")
      res.asInstanceOf[Map[String,Object]].asJava
    }

   // val t = datastore.beginTransaction()
    
    

    val res = json.asObject.map(obj => {
      val snapshot = datastore.document(s"$kind/$id").get.get
      val ret = if(snapshot.exists()){
        snapshot.getReference.update(doit(Map())(obj))
      }
      else{
         snapshot.getReference.create(doit(Map())(obj))
      }
      ret
      })
    
    log.warning(s"$kind/$id : @${res.get.get.getUpdateTime}")

  }

  private def props(p: java.util.Map[String,Any]) = p.asScala.toList.map({ case (name: String, o) => (name, convertToJson(o)) }).asJava

  private def convertToJson(obj: Any): Json = {

    obj match {
      case null => Json.Null
      case d: java.lang.Double => Json.fromDoubleOrNull(d)
      case s: String => Json.fromString(s)
      case b: Boolean => Json.fromBoolean(b)
      case ee: java.util.Map[String,Any] => Json.fromFields(props(ee))
      case l: java.util.List[_] => Json.fromValues(l.filter(_ != null).map(convertToJson(_)))
      case _ => throw new RuntimeException(s"no match found for ${obj.getClass.getName}")
    }

  }

  private def load[T <: Entity](kind: String, id: String, decoder: Decoder[T])(implicit tag: ClassTag[T]): T = {

    entityToObj(datastore.document(s"$kind/$id").get.get.getData.asInstanceOf[java.util.Map[String,Any]], decoder)

  }

  private def entityToObj[T](entity: java.util.Map[String,Any], decoder: Decoder[T])(implicit tag:ClassTag[T]) = {
    val json: Json = Json.fromFields(props(entity))

    val r = decoder.decodeJson(json)

    r.fold(fa => throw fa, fb => fb)
  }
  
}