package quizleague.rest

import java.util.concurrent.ConcurrentHashMap

import javax.ws.rs.core.EntityTag
import java.util.UUID

object EtagCache {
  private val cache = new ConcurrentHashMap[String,EntityTag]
  
  def add(id:String):EntityTag = {
    val etag = EntityTag.valueOf(s""""${UUID.randomUUID().toString()}"""")
    cache.put(id, etag)
    etag
  }
  
  def remove(id:String) = cache.remove(id)
  
  def get(id:String) = if(!cache.containsKey(id)) add(id) else cache.get(id)
  
 }