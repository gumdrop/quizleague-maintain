package quizleague.web.core

import scalajs.js
import js.Dynamic.literal
import scala.scalajs.js.JSConverters._
import com.felstar.scalajs.vue.Vue
import rxscalajs.facade.ObservableFacade
import rxscalajs.Observable
import rxscalajs.Subject
import rxscalajs.subjects.ReplaySubject
import quizleague.web.util.rx.RefObservable
import scala.scalajs.js.UndefOr
import com.felstar.scalajs.vue.VueComponent
import com.felstar.scalajs.vue.VueRxComponent
import quizleague.web.util.Logging._
import com.felstar.scalajs.vue.VuetifyComponent
import com.felstar.scalajs.vue.VuetifyComponent


trait RouteComponent extends Component{

  val name = ""

}


trait Component {

  type facade <: VueComponent with VueRxComponent
  
  val name: String
  def template: String
  private def props: js.Array[String] = @@()
  private def subscriptions: Map[String, facade => Observable[Any]] = Map()
  private var addedSubs: Map[String, facade => Observable[Any]] = Map()
  private var addedSubParams: List[((String, String))] = List()
  private var addedProps:List[String] = List()
  private var addedMethods:Map[String, js.Function] = Map()
  private var addedComputed:Map[String, js.Any] = Map()
  private var addedDataFn:Map[String, facade => Any] = Map()
  private var addedData:Map[String,Any] = Map()
  private var addedComponents:List[Component] = List()
  private var addedWatch: Map[String, ((facade,js.Any) => Unit)] = Map()
  private def data: facade => Map[String, Any] = c => Map()
  private def methods: Map[String, js.Function] = Map()
  def components: js.Array[Component] = addedComponents.toJSArray
  def mounted: js.Function = null
  def activated:js.Function = null
  def created:js.Function = null
  def beforeCreate:js.Function = null

  var observables = js.Dictionary[js.Dictionary[Any]]() 
  val empty = new js.Object
  
  private val commonMethods:Map[String, js.Function] = Map("async" -> (((c:facade, in:UndefOr[RefObservable[js.Dynamic]]) => {
    
    println("ql-web : entering async")
    if(in.isDefined){
    
    val obs = in.get
    
    val retval = observables.get(obs.id)
    
    
    def sub() = {
      val a = js.Dictionary[Any]()
      c.$subscribeTo(obs.inner, (b:js.Dynamic) => c.$nextTick({() => Vue.util.extend(a,b);c.$forceUpdate()}))
      observables += ((obs.id,a))
      a
    }
    if(retval.isEmpty) sub() else retval.get
    }
    else empty

  }):js.ThisFunction))
  
  protected final def subscription(name:String,linkedProps:String*)(fn:facade => Observable[Any]) {
    addedSubs  = addedSubs + ((name, fn))
    addedSubParams = addedSubParams ++ linkedProps.map((_,name))
  }
  
  
  protected final def props(names:String*){
    addedProps = addedProps ++ names
  }
  
  protected final def prop(name:String){
    addedProps = addedProps :+ name
  }
  
  protected final def method(name:String)(fn:js.Function){
    addedMethods = addedMethods + ((name, fn))
  }
  
  protected final def data(name:String, value:Any){
    addedData = addedData + ((name, value))
  }
  
  protected final def data(name:String)(fn:facade => Any){
    addedDataFn = addedDataFn + ((name, fn))
  }
  
  protected final def components(comps:Component*){
    addedComponents = addedComponents ++ comps
  }
  
  protected final def computed(name:String)(fn:js.Function){
    addedComputed = addedComputed + ((name, fn))
  }
  
  protected final def computedGetSet(name:String)(get:js.Function, set:js.Function){
    addedComputed = addedComputed + ((name, js.Dynamic.literal(get = get, set = set)))
  }
  
  protected final def watch(name:String)(fn:(facade,js.Any) => Unit){
    addedWatch = addedWatch + ((name, fn))
  }
  


  
  def apply():js.Dynamic = {

    def update(subject: Subject[Any])(fn: facade => Observable[Any])(c: facade) = {
       c.$subscribeTo(
          fn(c).inner, 
          subject.inner)
      subject.inner
    }

    
    def makeSubscriptions(c:facade) = {

      val subscriptions = addedSubs
      val subParams = addedSubParams
      
      val watchSubs = subParams.toMap.map{case (k, v) => {
        val subject = ReplaySubject[Any]()
        
        val subscription = update(subject)(subscriptions(v)) _
        val watch = c.$watchAsObservable(k).subscribe( x=> subscription(c))
        
        (v, subscription)
        
      }}
      
      val subs = subscriptions.filterKeys(k => !subParams.toMap.values.exists(_ == k)).map{
        case (k,v) => ((k, (c:facade) => v(c).inner))
      }
      
      watchSubs ++ subs
      
    }
    

    
    val retval = literal(
      template = template,
      props = addedProps.toJSArray,
      data = ((v: facade) => (addedData ++ addedDataFn.map{case(k,fn) => (k,fn(v))}).toJSDictionary): js.ThisFunction,
      watch = (addedWatch.map { case (k, v) => (k, v: js.ThisFunction) }).toJSDictionary,
      subscriptions = ((c: facade) => makeSubscriptions(c).map { case (k, v) => (k, v(c)) }.toJSDictionary): js.ThisFunction,

      methods = (commonMethods ++ addedMethods).toJSDictionary,
      computed = addedComputed.toJSDictionary,
      components = addedComponents.map(c => ((c.name, c()))).toMap.toJSDictionary,
      mounted = mounted,
      activated = activated,
      created = created,
      beforeCreate = beforeCreate
           
    )
    
    retval
  }

}

@js.native
trait IdComponent extends VueComponent with VueRxComponent with VuetifyComponent{
  val id:String = js.native  
}
