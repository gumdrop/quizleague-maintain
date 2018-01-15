package quizleague.rest

import javax.servlet._
import java.util.logging.Logger
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse
import java.util.logging.Level


  object URLRewriteFilter {
  val LOG: Logger = Logger.getLogger(classOf[URLRewriteFilter].getName())
}

class URLRewriteFilter extends Filter {
  import URLRewriteFilter.LOG
  var context: ServletContext = null

  override def doFilter(arg0: ServletRequest, arg1: ServletResponse,
    arg2: FilterChain): Unit = {
    val rd = arg0.getRequestDispatcher("/")
    
    rd.forward(arg0, arg1)

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

class HistoryApiFallbackFilter extends Filter {

	val log = Logger.getLogger(this.getClass.getName)
  val REENTRANCY_KEY = classOf[HistoryApiFallbackFilter].getName

	val FORWARD_PATH_CONFIG_PARAMETER = "forwardPath"
	var forwardPath:String = null;

	/** */
	def isGet(method:String) = method == "GET"
	def hasHeader(header:String) = header != null && header.length() > 0
	def isApplicationJson(header:String) = header.contains("application/json")
	def acceptsHtml(header:String) = header.contains("text/html") || header.contains("*/*")
	def pathIncludesDot(path:String) = path != null && path.indexOf('.') != -1
	

	/** */
	def doFilter(servletRequest:ServletRequest, servletResponse:ServletResponse, filterChain:FilterChain) {
	  
	  def safeLC(string:String) = if(string == null) "" else string.toLowerCase()
	  
	  val request = servletRequest.asInstanceOf[HttpServletRequest]
		val response = servletResponse.asInstanceOf[HttpServletResponse]

		val method = request.getMethod().toUpperCase
		val accept = safeLC(request.getHeader("Accept"));
		val requestURI = request.getRequestURI();

		val reentrancyKey = request.getAttribute(REENTRANCY_KEY);

		var doFilter = false;

		if (reentrancyKey != null ||
			!isGet(method) ||
			!hasHeader(accept) ||
			isApplicationJson(accept) ||
			!acceptsHtml(accept) ||
			pathIncludesDot(requestURI)) {

			doFilter = true;
		}

		if (log.isLoggable(Level.FINER))
			log.finer("doFilter: " + doFilter + ", requestURI: " + requestURI);

		if (doFilter) {
			filterChain.doFilter(servletRequest, servletResponse);
		} else {
			// Prevent the next request from hitting this filter
			request.setAttribute(REENTRANCY_KEY, true);
			request.getRequestDispatcher(forwardPath).forward(request, response);
		}
	}


	override def destroy(){}

	/** */
	
	override def init(filterConfig:FilterConfig) {
		forwardPath = filterConfig.getInitParameter(FORWARD_PATH_CONFIG_PARAMETER);
		if (forwardPath == null) {
			throw new ServletException("Please set the '" + FORWARD_PATH_CONFIG_PARAMETER + "' servlet filter config as part of the " + REENTRANCY_KEY);
		}
	}
}
