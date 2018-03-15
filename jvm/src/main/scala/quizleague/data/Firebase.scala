package quizleague.data


import com.google.auth.oauth2.GoogleCredentials
import quizleague.domain.Entity
import io.circe._
import reflect._
import scala.collection.JavaConverters._
import com.google.cloud.firestore.FirestoreOptions
import com.google.auth.Credentials
import java.util.logging.Logger

object Storage {
  
  val log = Logger.getLogger(this.getClass.toString())
  
  val options = FirestoreOptions.getDefaultInstance.toBuilder()
  .setProjectId("chiltern-ql-firestore").build


  lazy val datastore = options.getService

  def delete[T <: Entity](entity: T)(implicit tag: ClassTag[T]): Unit ={
    val kind = makeKind
    datastore.document(s"$kind/${entity.id}").delete()
  }
  
  def save[T <: Entity](entity: T)(implicit tag: ClassTag[T], encoder: Encoder[T]): Unit = {
    val kind = makeKind
    save(kind, entity.id, encoder(entity))
  }
  
  def saveAll[T <: Entity](entities: List[T])(implicit tag: ClassTag[T], encoder: Encoder[T]):Unit = {
     val kind = makeKind
     
     val objrefs = entities.map(e => (datastore.document(s"$kind/${e.id}"),asMap(encoder(e).asObject.get)))
     
     val batchSets = objrefs.grouped(400)
     
     batchSets.foreach( l => {
     
       val batch = datastore.batch()
       
       l.foreach({case (r,o) => batch.set(r,o)})
       
       batch.commit()
     })
  }
  
    def deleteAll[T <: Entity](entities: List[T])(implicit tag: ClassTag[T]):Unit = {
     val kind = makeKind
     
     val objrefs = entities.map(e => (datastore.document(s"$kind/${e.id}")))
     
     val batchSets = objrefs.grouped(400)
     
     batchSets.foreach( l => {
     
       val batch = datastore.batch()
       
       l.foreach(d => batch.delete(d))
       
       batch.commit()
     })
  }

  def load[T <: Entity](id: String)(implicit tag: ClassTag[T], decoder: Decoder[T]): T = load(makeKind, id, decoder)

  def list[T <: Entity](implicit tag: ClassTag[T], decoder: Decoder[T]): List[T] = {
    datastore.collection(makeKind).get.get.getDocuments.asScala.map(d => entityToObj(d.getData.asInstanceOf[java.util.Map[String,Any]], decoder)).toList
  }

  private def makeKind(implicit tag: ClassTag[_]) = tag.runtimeClass.getSimpleName.toLowerCase

  private def save(kind: String, id: String, json: Json): Unit = {

    val t = datastore.runTransaction( tr => {
      val res = json.asObject.map(obj => {
      val ent = asMap(obj)
      val ref = datastore.document(s"$kind/$id")
      ref.set(ent)})
      res
    })

    t.get()

  }
  
  private def asMap(obj: JsonObject) = {
    
    def handleField(name: String, json: Json) =  ((name, convertObject(json)))

    def convertObject(json: Json): Any = {
      val value: Option[Any] = if (json.isNumber) json.asNumber.map(_.toDouble)
      else if (json.isString) json.asString.map(_.toString())
      else if (json.isBoolean) json.asBoolean
      else if (json.isObject) json.asObject.map(doit _)
      else if (json.isArray) json.asArray.map(v => asArrayList(v.map(o => convertObject(o))))
      else if (json.isNull) None
      else None

      value.getOrElse(null)
    }

    def asArrayList[T](v: Vector[T]) = v.toList.asJava

    def doit(obj: JsonObject) = {

      val res = Map() ++ obj.toVector.map((handleField _).tupled)
      
      res.asInstanceOf[Map[String,Object]].asJava
    }
    
    doit(obj)
    
  }

  private def props(p: java.util.Map[String,Any]):Iterable[(String,Json)] = p.asScala.toList.map({ case (name: String, o) => (name, convertToJson(o)) })

  private def convertToJson(obj: Any): Json = {

    obj match {
      case null => Json.Null
      case d: java.lang.Double => Json.fromDoubleOrNull(d)
      case l: java.lang.Long => Json.fromLong(l)
      case s: String => Json.fromString(s)
      case b: Boolean => Json.fromBoolean(b)
      case ee: java.util.Map[String,Any] => Json.fromFields(props(ee))
      case l: java.util.List[_] => Json.fromValues(l.asScala.filter(_ != null).map(convertToJson(_)))
      case _ => throw new RuntimeException(s"no match found for ${obj.getClass.getName}")
    }

  }

  private def load[T <: Entity](kind: String, id: String, decoder: Decoder[T])(implicit tag: ClassTag[T]): T = {

    entityToObj(datastore.document(s"$kind/$id").get.get.getData.asInstanceOf[java.util.Map[String,Any]], decoder)

  }

  private def entityToObj[T](entity: java.util.Map[String,Any], decoder: Decoder[T]) = {
    val json: Json = Json.fromFields(props(entity))

    val r = decoder.decodeJson(json)

    r.fold(fa => throw fa, fb => fb)
  }
  
}