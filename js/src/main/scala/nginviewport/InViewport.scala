package nginviewport

import angulate2.std._
import scalajs.js
import scalajs.js._
import angulate2.core.{ElementRef,EventEmitter}
import DynamicImplicits.{number2dynamic,boolean2dynamic, truthValue}
import quizleague.web.util.Logging._
import angulate2.core.AfterViewInit


@Directive(
  selector = "[in-viewport]"
)
class InViewport(_el:ElementRef) extends AfterViewInit{

  val window = Dynamic.global

  @Output("inViewport")
  var inViewport:EventEmitter[js.Any] = new EventEmitter()

  @HostListener("#sidenav-content:scroll")
  def onScroll() = check()
  
  @HostListener("window:resize")
  def onResize() = check()
  
  override def ngAfterViewInit() {
    check();
  }


  def check(partial:Boolean = true, direction:String = "both") {
    
    val el = this._el.nativeElement.asInstanceOf[Dynamic];


    val elSize = (el.offsetWidth * el.offsetHeight);
    
    log(elSize,"elSize")
    
    val rec = el.getBoundingClientRect()
    
    log(s"top:${rec.top},bottom:${rec.bottom}, left:${rec.left}, right:${rec.right}",s"rec")

    val vp = Dynamic.literal(width = window.innerWidth, height = window.innerHeight)

    log(vp,s"vp")
    
    val tViz = rec.top >= 0 && rec.top < vp.height
    val bViz = rec.bottom > 0 && rec.bottom <= vp.height

    val lViz = rec.left >= 0 && rec.left < vp.width
    val rViz = rec.right > 0 && rec.right <= vp.width

    val vVisible = if(partial) tViz || bViz else tViz && bViz;
    val hVisible = if(partial) lViz || rViz else lViz && rViz;

    val event = Dynamic.literal(target = el, value = false)

    if (direction == "both") {
      event.value = if(elSize && vVisible && hVisible) true else false;
    }
    else if (direction == "vertical") {
      event.value = if(elSize && vVisible) true else false;
    }
    else if (direction == "horizontal") {
      event.value = if(elSize && hVisible) true else false;
    }

    inViewport.emit(event);
  }
}