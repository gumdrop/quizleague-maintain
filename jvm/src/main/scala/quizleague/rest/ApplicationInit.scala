package quizleague.rest

import javax.servlet.ServletContextListener
import quizleague.data.Storage
import quizleague.domain.ApplicationContext
import quizleague.util.json.codecs.DomainCodecs._

class ApplicationInit extends ServletContextListener {

  override def contextDestroyed(evt: javax.servlet.ServletContextEvent): Unit = {}
  override def contextInitialized(evt: javax.servlet.ServletContextEvent): Unit = {
    
    Storage.load[ApplicationContext](ApplicationContext.singletonId)
  
  }

}