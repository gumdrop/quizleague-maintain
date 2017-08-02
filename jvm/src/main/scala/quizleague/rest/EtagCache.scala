package quizleague.rest

import javax.ws.rs.core.EntityTag
import java.util.UUID
import javax.cache.CacheManager
import java.util.Collections
import scala.collection.JavaConverters._

object EtagCache {
  
  private val cache = CacheManager.getInstance.getCacheFactory.createCache(Collections.emptyMap()).asInstanceOf[java.util.Map[String,String]].asScala
  
  def add(id:String):EntityTag = {
    val etag = EntityTag.valueOf(s""""${UUID.randomUUID().toString()}"""")
    cache.put(factorId(id), etag.getValue)
    etag
  }
  
  def remove(id:String) = cache.remove(factorId(id))
  
  def get(id:String) = cache.get(factorId(id)).fold(add(id))(t => EntityTag.valueOf(s""""$t""""))
  
  private def factorId(id:String) = s"$id-etag"
  
 }