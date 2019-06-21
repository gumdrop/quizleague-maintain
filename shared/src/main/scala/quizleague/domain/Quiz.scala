package quizleague.domain

case class Quiz(
                 id: String,
                 user: Ref[SiteUser],
                 description: String,
                 retired: Boolean = false
               ) extends Entity

case class QuizQuestion(
                         id: String,
                         question: Array[Byte],
                         answer: Array[Byte],
                         retired: Boolean = false
                       ) extends Entity


