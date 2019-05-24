package quizleague.web.store

import scalajs.js
import js.Dynamic.{literal => $}
import js.JSON
import firebase._
import quizleague.firestore.{Connection => conn}
import scala.scalajs.js.JSConverters._
import scala.io.Source


object Firestore {

    val config = $(
      apiKey= conn.apiKey,
      authDomain= conn.authDomain,
      databaseURL=  conn.databaseURL,
      projectId = conn.projectId,
      storageBucket= conn.storageBucket,
      messagingSenderId= conn.messagingSenderId)

  
   Firebase.initializeApp(config)
      
   private val firestore = Firebase.firestore()
   firestore.enablePersistence($(experimentalTabSynchronization=true)).`then`(x => {})
   
   val db = firestore
   
   def setAuthContext(){

    Firebase.auth().onAuthStateChanged( (user: User) =>

      if (user == null) {
        val provider = new auth.GoogleAuthProvider()
        Firebase.auth().signInWithRedirect(provider)
      }

    )
  }
}