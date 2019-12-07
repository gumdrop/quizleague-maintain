package quizleague.web.site

import quizleague.web.model.{Fixture, Fixtures}
import rxscalajs.Observable
import rxscalajs.Observable.combineLatest

import scala.scalajs.js
import js.JSConverters._

package object fixtures {
  def fixturesToFixtureList(fixtures:Observable[js.Array[Fixtures]]):Observable[js.Array[Fixture]] = {
    fixtures.flatMap(fx => combineLatest(fx.map(_.fixture).toSeq)).map(_.flatten.toJSArray)
  }
}
