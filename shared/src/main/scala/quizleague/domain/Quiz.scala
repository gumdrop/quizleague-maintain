package quizleague.domain

case class Quiz(
 id: String,
 owner: Ref[SiteUser],
 description: String,
 rightValue:Int,
 wrongValue:Int,
 retired: Boolean = false
) extends Entity

object QuestionSetState extends Enumeration {
  type QuestionSetState = Value
  val fresh, inprogress, completed = Value
}

import QuestionSetState._

case class QuestionSet(
  id:String,
  name:String,
  state:QuestionSetState,
  currentQuestionIndex:Int,
  retired:Boolean = false
) extends Entity

case class QuizQuestion(
 id: String,
 question: String,
 answer: String,
 result:Option[QuestionResult],
 retired: Boolean = false
) extends Entity

case class QuestionResult(
 right:Option[Ref[SiteUser]],
 wrong:List[Ref[SiteUser]],
 invalidated:Boolean

)

case class QuizResult(
  id:String,
  competitors:List[Ref[SiteUser]],
  scores:List[(Ref[SiteUser],Integer)],
  retired: Boolean = false
) extends Entity

