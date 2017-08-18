package quizleague.rest

import javax.servlet.ServletContextListener
import quizleague.data.Storage
import quizleague.domain.ApplicationContext
import quizleague.util.json.codecs.DomainCodecs._
import java.util.logging.Logger

class ApplicationInit extends ServletContextListener {

  val LOG = Logger.getLogger(this.getClass.getName)
  
  override def contextDestroyed(evt: javax.servlet.ServletContextEvent): Unit = {}
  override def contextInitialized(evt: javax.servlet.ServletContextEvent): Unit = {
    
    try Storage.load[ApplicationContext](ApplicationContext.singletonId)
    catch {
      case e:Exception => LOG.severe(s"ApplicationContext not found for id ${ApplicationContext.singletonId}")
    }
  
  }

}