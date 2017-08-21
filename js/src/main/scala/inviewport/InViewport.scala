package inviewport

import scala.scalajs.js
import scala.scalajs.js._
import scala.scalajs.js.DynamicImplicits.{ boolean2dynamic, number2dynamic, truthValue }

import angulate2.core.{ ElementRef, EventEmitter }
import angulate2.core.AfterViewInit
import angulate2.std._
import rxjs.BehaviorSubject
import rxjs.Observable
import rxjs.core.Subscription


@Directive(
  selector = "[in-viewport]"
)
class InViewport(_el:ElementRef, service:InViewportService) extends OnInit with OnDestroy{

  val window = Dynamic.global
  var scrollSub:Subscription = _

  @Output("inViewport")
  val inViewport:EventEmitter[js.Any] = new EventEmitter()
  
  @HostListener("window:resize")
  def onResize() = check()
  
  override def ngOnInit() {
    check();
    scrollSub = service.get("scroll").subscribe(b => check())
  }
  
  override def ngOnDestroy {
    scrollSub.unsubscribe()
  }
  
  def check(partial:Boolean = true, direction:String = "both") {
    
    val el = _el.nativeElement.asInstanceOf[Dynamic];

    val elSize = (el.offsetWidth * el.offsetHeight);
    
    val rec = el.getBoundingClientRect()

    val vp = Dynamic.literal(width = window.innerWidth, height = window.innerHeight)
    
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
      event.value = if(elSize && vVisible) true else false;service.get("scroll").subscribe(b => check())
    }
    else if (direction == "horizontal") {
      event.value = if(elSize && hVisible) true else false;
    }

    inViewport.emit(event);
  }
}



@Directive(
  selector = "[in-viewport-scroll]"
)
class InViewportScrollWatcher(service:InViewportService){
   
  @HostListener("scroll")
  def onScroll() = service.fire("scroll")

}

@Injectable
class InViewportService{
  var subjects = Map[String,BehaviorSubject[Boolean]]().withDefaultValue(new BehaviorSubject())
  
  def get(name:String):Observable[Boolean] = subjects(name).debounceTime(100)
  
  def fire(name:String) = subjects(name).next(true)
}
