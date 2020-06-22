package quizleague.data

import org.scalatest.{FlatSpec, Matchers}
import quizleague.domain.{Key, User}

class StorageSpec extends FlatSpec with Matchers {
  val storage = new StorageUtils{}


  "A domain object with no key" should "have a ref with typename and id only" in {
    val user = User("id","","")
    val ref = storage.ref(user)
    ref.id should be (user.id)
    ref.typeName should be ("user")
    ref.key should be (None)

  }

  "A domain object with a key" should "have a ref with typename, id and key" in {
    val key = Key(None, "user", "id")
    val user = User(key.id,"","").withKey(key)
    val ref = storage.ref(user)
    ref.id should be (user.id)
    ref.typeName should be (key.entityName)
    ref.key should be (Some(key))

  }
}
