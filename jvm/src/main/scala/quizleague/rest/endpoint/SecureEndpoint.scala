package quizleague.rest.endpoint

import javax.ws.rs.Path
import javax.ws.rs.core._
import javax.ws.rs.container._
import quizleague.rest.EtagSupport
import quizleague.rest.GetEndpoints
import quizleague.rest.PutEndpoints
import quizleague.domain._
import javax.ws.rs.ext._
import org.apache.commons.io.IOUtils
import java.nio.charset.StandardCharsets
import quizleague.util.encrypt.Crypto
import java.io.ByteArrayInputStream
import java.util.concurrent.ConcurrentHashMap
import quizleague.util.encrypt.QLSessionInfo
import scala.collection.JavaConversions._
import scala.reflect.ClassTag
import io.circe.Decoder
import io.circe.Encoder

@Path("/secure")
class SecureEndpoint(
  @Context override val request:Request,
  @Context headers:HttpHeaders,
  @Context override val uriInfo:UriInfo
) extends GetEndpoints with PutEndpoints with EtagSupport{
  
  override val defaultCacheAge = 0
  override val shortCacheAge = 0

  preChecks()
  
  override protected def out[T <: Entity](id: String, cacheAge:Int = defaultCacheAge)(implicit tag: ClassTag[T], dec: Decoder[T], enc: Encoder[T]):Response = {
     
    Response.fromResponse(super.out(id, cacheAge)).header(QLSessionInfo.headerName, headers.getHeaderString(QLSessionInfo.headerName)).build
  }
}

class EncryptionDynamicBinding extends DynamicFeature {
 

   override def configure( resourceInfo:ResourceInfo,  context:FeatureContext):Unit = {
        if (classOf[SecureEndpoint].equals(resourceInfo.getResourceClass())){
            context.register(classOf[EncryptionInterceptor]);
        }
    }
}

class EncryptionInterceptor extends ReaderInterceptor with WriterInterceptor {
 
    override def aroundReadFrom(context:ReaderInterceptorContext) = {
        val sessionId = context.getHeaders.get(QLSessionInfo.headerName).head
        val content = IOUtils.toString(context.getInputStream, StandardCharsets.UTF_8)
        val decrypted = Crypto.decrypt(content, SessionStore.get(sessionId).password)
        context.setInputStream(new ByteArrayInputStream(decrypted.getBytes(StandardCharsets.UTF_8)));
        context.proceed();
    }
    
    override def aroundWriteTo(context:WriterInterceptorContext):Unit = {
      
       val sessionId = context.getHeaders.get(QLSessionInfo.headerName).head.toString
       context.setEntity(Crypto.encrypt(context.getEntity.toString(), SessionStore.get(sessionId).password))
       context.proceed()
    }
}

case class Session(password:String)

private[endpoint] object SessionStore{
  
  private val cache = new ConcurrentHashMap[String,Session]
  
  def get(id:String) = cache.get(id)
  
  
}



