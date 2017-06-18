package quizleague.rest

import javax.servlet._
import java.util.logging.Logger
import javax.servlet.http.HttpServletRequest


  object URLRewriteFilter {
  val LOG: Logger = Logger.getLogger(classOf[URLRewriteFilter].getName())
}

class URLRewriteFilter extends Filter {
  import URLRewriteFilter.LOG
  var context: ServletContext = null

  override def doFilter(arg0: ServletRequest, arg1: ServletResponse,
    arg2: FilterChain): Unit = {
    arg0.getRequestDispatcher("/index.html").forward(
      arg0, arg1)

  }

  override def init(arg0: FilterConfig): Unit = {
    context = arg0.getServletContext();
    return
  }

  override def destroy() = {}

}

class MaintainURLRewriteFilter extends Filter {
  override def doFilter(arg0: ServletRequest, arg1: ServletResponse,
    arg2: FilterChain): Unit =  arg0.getRequestDispatcher("/maintain/index.html").forward(arg0, arg1)
  
  override def init(arg0: FilterConfig): Unit = {}

  override def destroy() = {}

}

class PassThroughFilter extends Filter {
  override def doFilter(arg0: ServletRequest, arg1: ServletResponse,
    arg2: FilterChain): Unit = {
    val req = arg0.asInstanceOf[HttpServletRequest]
    val uri = req.getRequestURI 
    arg0.getRequestDispatcher(uri).forward(arg0, arg1)

  }

  override def init(arg0: FilterConfig): Unit = {}

  override def destroy() = {}
}
