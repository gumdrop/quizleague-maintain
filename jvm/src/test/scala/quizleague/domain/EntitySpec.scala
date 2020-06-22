package quizleague.domain

import org.scalatest.{FlatSpec, Matchers}

class EntitySpec  extends FlatSpec with Matchers {

  "A domain object" should "have a None key initially" in {
    val user = User("id","name","email")

    user.key should be (None)
  }

  "A domain object" should "have a have its key set successfully" in {
    val key = Key(Some("parent/id"), "child", "id")
    val user = User("id","name","email").withKey(key)

    user.key should be (Some(key))
  }

}
