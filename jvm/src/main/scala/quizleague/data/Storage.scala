package quizleague.data

import com.google.appengine.api.datastore._
import quizleague.domain.Entity
import com.google.appengine.api.datastore.{ Entity => Ent }
import io.circe._
import reflect._
import scala.collection.JavaConversions._
import scala.collection.JavaConverters._
import java.util.ArrayList
import java.util.Collections
import javax.cache.CacheManager
import java.util.Date

object Storage {

  def nullList[T] = {
    val list = new ArrayList[T]
    list.add(null.asInstanceOf[T])
    list
  }
  
  val cache = CacheManager.getInstance.getCacheFactory.createCache(Collections.emptyMap()).asInstanceOf[java.util.Map[String,Entity]].asScala
  
  private def cacheId(id:String, kind:String) = s"$kind-$id"
  def toCache[T <: Entity](entity:T, kind:String):T = {cache.put(cacheId(entity.id, kind) ,entity);entity}
  def fromCache[T <: Entity](id:String,kind:String):Option[T] = cache.get(cacheId(id, kind)).asInstanceOf[Option[T]]


  def datastore = DatastoreServiceFactory.getDatastoreService

  def save[T <: Entity](entity: T)(implicit tag: ClassTag[T], encoder: Encoder[T]): Key = {
    val kind = makeKind
    save(kind, entity.id, encoder(toCache(entity, kind)))
  }

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
      case t: Text => Json.fromString(t.getValue)
      case b: Boolean => Json.fromBoolean(b)
      case ee: EmbeddedEntity => Json.fromFields(props(ee))
      case l: java.util.List[_] => Json.fromValues(l.filter(_ != null).map(convertToJson(_)))
      case _ => throw new RuntimeException(s"no match found for ${obj.getClass.getName}")
    }

  }

  private def load[T <: Entity](kind: String, id: String, decoder: Decoder[T])(implicit tag: ClassTag[T]): T = {

    val cached = fromCache(id, kind)
    
    cached.getOrElse(toCache(entityToObj(logTime(s"from store, $kind",() => datastore.get(KeyFactory.createKey(kind, id))), decoder),kind))

  }

  private def entityToObj[T](entity: Ent, decoder: Decoder[T])(implicit tag:ClassTag[T]) = {
    val json: Json = logTime(s"to json - ${makeKind(tag)} ",() => Json.fromFields(props(entity)))

    val r = logTime(s"decode, ${makeKind(tag)}", () => decoder.decodeJson(json))

    r.fold(fa => throw fa, fb => fb)
  }
  
  private def logTime[U](message:String,fn:() => U):U = {
    val now = new Date().getTime
    val r  = fn()
    println(s"$message, ${new Date().getTime - now}")
    r
  }
}