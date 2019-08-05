package quizleague.web.model

import quizleague.web.util.rx.RefObservable

class Quiz (
  val id: String,
  val owner: RefObservable[SiteUser],
  val description: String,
  val retired: Boolean

) extends Model

class QuizQuestion(
  val id: String,
  val question: String,
  val answer: String
) extends Model
