package quizleague.util

package object collection {
  def Desc[T : Ordering] = implicitly[Ordering[T]].reverse
}