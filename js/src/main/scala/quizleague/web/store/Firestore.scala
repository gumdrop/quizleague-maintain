package quizleague.web.store

import scalajs.js
import js.Dynamic.literal
import firebase._


object Firestore {
    val config = literal(      
    apiKey= "AIzaSyCTnCW1euWGpRohoEBESIdNEASM7rQ5gkY",
    authDomain= "ql-firestore-2.firebaseapp.com",
    databaseURL= "https=//ql-firestore-2.firebaseio.com",
    projectId= "ql-firestore-2",
    storageBucket= "ql-firestore-2.appspot.com",
    messagingSenderId= "659931577179"
)
  
   Firebase.initializeApp(config)
      
   val db = Firebase.firestore()
   
   def setAuthContext(){

    Firebase.auth().onAuthStateChanged({ (user: User) =>

      if (user == null) {
        val provider = new auth.GoogleAuthProvider()
        Firebase.auth().signInWithRedirect(provider)
      }

    }: js.Function)
  }
}