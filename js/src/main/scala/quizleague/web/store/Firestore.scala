package quizleague.web.store

import scalajs.js
import js.Dynamic.literal
import firebase._
import scala.scalajs.js.JSConverters._


object Firestore {
//    val config = literal(
//    apiKey= "AIzaSyBs6LpcOSpLMlKlzw0aPB6Ie-39mqlKrm8",
//    authDomain= "chiltern-ql-firestore.firebaseapp.com",
//    databaseURL= "https://chiltern-ql-firestore.firebaseio.com",
//    projectId= "chiltern-ql-firestore",
//    storageBucket= "chiltern-ql-firestore.appspot.com",
//    messagingSenderId= "891716942638"
//)

    val config = literal(
    apiKey= "AIzaSyCTnCW1euWGpRohoEBESIdNEASM7rQ5gkY",
    authDomain= "ql-firestore-2.firebaseapp.com",
    databaseURL= "https=//ql-firestore-2.firebaseio.com",
    projectId= "ql-firestore-2",
    storageBucket= "ql-firestore-2.appspot.com",
    messagingSenderId= "659931577179")

  
   Firebase.initializeApp(config)
      
   private val firestore = Firebase.firestore()
   firestore.enablePersistence(literal(experimentalTabSynchronization=true)).`then`(x => {})
   
   val db = firestore
   
   def setAuthContext(){

    Firebase.auth().onAuthStateChanged({ (user: User) =>

      if (user == null) {
        val provider = new auth.GoogleAuthProvider()
        Firebase.auth().signInWithRedirect(provider)
      }

    }: js.Function)
  }
}