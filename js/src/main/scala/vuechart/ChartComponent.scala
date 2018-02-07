package vuechart

import quizleague.web.core.Component
import scala.scalajs.js
import js.DynamicImplicits._
import com.felstar.scalajs.vue.VueRxComponent
import chartjs.chart._

@js.native
trait ChartComponent extends VueRxComponent{
  var chart:Chart
  val `type`:String
  val data:ChartData
  val options:ChartOptions
}

object ChartComponent extends Component{
  
  type facade = ChartComponent
  
  val name = "chart"
  val template = """
    <canvas ref="chart" :width="width" :height="height"></canvas>
"""
  
  props("type","data","options","width","height")
  data("chart",null)
  
  def createChart(c:facade) = c.chart = new Chart(c.$refs.chart, ChartParam(c.`type`, c.options, c.data))
  
  method("createChart")({createChart _}:js.ThisFunction)
  
  override val mounted = ({(c:facade) => createChart(c)}:js.ThisFunction)
  override val beforeDestroy = ({(c:facade) => c.chart.destroy()}:js.ThisFunction)
  
  watch("data")((c:facade,x:js.Any) => {c.chart.destroy();createChart(c)})
  
  watch("data.labels")((c:facade,x:js.Any) => c.chart.update())
  watch("data.labels")((c:facade,x:js.Any) => c.chart.update())
}