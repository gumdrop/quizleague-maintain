package quizleague.rest

import java.util.concurrent.ConcurrentHashMap

object EtagCache {
  private val cache = new ConcurrentHashMap[String,String]
  
  def add(id:String, payload:Any):String = {
    val etag = s""""${payload.hashCode().toString}""""
    cache.put(id, etag)
    etag
  }
  
  def remove(id:String) = cache.remove(id)
  
  def isMatch(id:String, etag:String) = etag == cache.get(id)
  
 }