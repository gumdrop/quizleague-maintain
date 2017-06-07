package quizleague.rest

import java.util.concurrent.ConcurrentHashMap
import javax.ws.rs.core.EntityTag

object EtagCache {
  private val cache = new ConcurrentHashMap[String,EntityTag]
  
  def add(id:String, payload:Any):EntityTag = {
    val etag = EntityTag.valueOf(payload.hashCode().toString)
    cache.put(id, etag)
    etag
  }
  
  def remove(id:String) = cache.remove(id)
  
  def get(id:String) = cache.get(id)
  
 }