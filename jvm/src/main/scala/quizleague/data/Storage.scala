package quizleague.data

import com.google.appengine.api.datastore._
import quizleague.domain.Entity
import com.google.appengine.api.datastore.{ Entity => Ent }
import io.circe._
import reflect._
import scala.collection.JavaConversions._
import scala.collection.JavaConverters._
import java.util.ArrayList

object Storage {

  def nullList[T] = {
    val list = new ArrayList[T]
    list.add(null.asInstanceOf[T])
    list
  }

  def datastore = DatastoreServiceFactory.getDatastoreService

  def save[T <: Entity](entity: T)(implicit tag: ClassTag[T], encoder: Encoder[T]): Key = save(makeKind, entity.id, encoder(entity))

  def load[T <: Entity](id: String)(implicit tag: ClassTag[T], decoder: Decoder[T]): T = load(makeKind, id, decoder)

  def list[T <: Entity](implicit tag: ClassTag[T], decoder: Decoder[T]): List[T] = {
    val q: Query = new Query(makeKind)

    datastore.prepare(q).asIterable().map(entityToObj(_, decoder)).toList
  }

  private def makeKind(implicit tag: ClassTag[_]) = tag.runtimeClass.getSimpleName.toLowerCase

  private def save(kind: String, id: String, json: Json): Key = {

    def handleField(entity: PropertyContainer)(name: String, json: Json) = { entity.setProperty(name, convertObject(json)) }

    def convertObject(json: Json): Any = {
      val value: Option[Any] = if (json.isNumber) json.asNumber.map(_.toDouble)
      else if (json.isString) json.asString.map(_.toString()).map(s => if (s.length() > 1500) new Text(s) else s)
      else if (json.isBoolean) json.asBoolean
      else if (json.isObject) json.asObject.map(doit(new EmbeddedEntity()))
      else if (json.isArray) json.asArray.map(v => asArrayList(v.map(o => convertObject(o))))
      else if (json.isNull) None
      else None

      value.getOrElse(null)
    }

    def asArrayList[T](v: Vector[T]): ArrayList[T] = if (v.isEmpty) nullList[T] else new ArrayList(v.toList.asJava)

    def doit[T <: PropertyContainer](entity: T)(obj: JsonObject): T = {

      obj.toVector.foreach((handleField(entity) _).tupled)

      entity
    }

    val t = datastore.beginTransaction()

    val key = json.asObject.map(obj => datastore.put(t, doit(new Ent(kind, id))(obj)))

    t.commit()

    key.get
  }

  private def props(p: PropertyContainer) = p.getProperties.asScala.toList.map({ case (name: String, o) => (name, convertToJson(o)) })

  private def convertToJson(obj: Any): Json = {

    obj match {
      case null => Json.Null
      case d: java.lang.Double => Json.fromDoubleOrNull(d)
      case s: String => Json.fromString(s)
      case b: Boolean => Json.fromBoolean(b)
      case ee: EmbeddedEntity => Json.fromFields(props(ee))
      case l: java.util.List[_] => Json.fromValues(l.filter(_ != null).map(convertToJson(_)))
      case _ => throw new RuntimeException(s"no match found for ${obj.getClass.getName}")
    }

  }

  private def load[T](kind: String, id: String, decoder: Decoder[T]): T = {

    val entity = datastore.get(KeyFactory.createKey(kind, id))

    entityToObj(entity, decoder)

  }

  private def entityToObj[T](entity: Ent, decoder: Decoder[T]) = {
    val json: Json = Json.fromFields(props(entity))

    val r = decoder.decodeJson(json)

    if (r.isLeft) throw r.left.get else r.right.get
  }
}