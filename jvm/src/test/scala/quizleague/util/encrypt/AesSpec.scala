package quizleague.util.encrypt

import org.scalatest._

class AesSpec extends FlatSpec with Matchers {
  
  "Crypto" should " encrypt and decrypt reliably" in {
    
    val text = "Some text here"
    
    val crypt = Crypto.encrypt(text, "password")
    
    val decrypt = Crypto.decrypt(crypt, "password")
    
    decrypt should be (text)
    
    crypt shouldNot be (text)    
  }
  
}