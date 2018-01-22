package quizleague.web.site.season

import quizleague.web.core._
import quizleague.web.model.Season
import scalajs.js

trait SeasonFormatComponent {
  this: Component =>

  def formatSeason(season: js.UndefOr[Season]) = season.fold("")(s => if (s.startYear == s.endYear) s.startYear.toString else s"${s.startYear}/${s.endYear}")

  method("formatSeason")(formatSeason _)
}