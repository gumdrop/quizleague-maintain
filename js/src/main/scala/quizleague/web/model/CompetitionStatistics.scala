package quizleague.web.model

import quizleague.web.util.rx.RefObservable
import scalajs.js


class CompetitionStatistics(

                           val id: String,
                           val competitionName: String,
                           val results: js.Array[ResultEntry] = js.Array()
                           )extends Model

class ResultEntry(
                        var seasonText: String,
                        val season: RefObservable[Season],
                        var competition:RefObservable[Competition],
                        var teamText: String,
                        val team: RefObservable[Team],
                        val position: Int = 1
                      ) extends js.Object

