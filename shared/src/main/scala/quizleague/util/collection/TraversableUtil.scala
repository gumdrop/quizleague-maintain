package quizleague.util.collection

object TraversableUtil {
  class IndexMemoizingFunction[A, B](f: (Int, A) => B) extends Function1[A, B] {
    private var index = 0
    override def apply(a: A): B = {
      val ret = f(index, a)
      index += 1
      ret
    }
  }

  def doIndexed[A, B](f: (Int, A) => B): A => B = {
    new IndexMemoizingFunction(f)
  }
}