package quizleague.web.store

import scalajs.js.Dynamic.literal
import firebase.firebase._

object Firestore {
    val config = literal(      
     apiKey = "AIzaSyAzKh8FB6M7DEF8bTsjNOnJWTRKzimUaWk",
    authDomain ="ql-firestore-trial.firebaseapp.com",
    databaseURL = "https://ql-firestore-trial.firebaseio.com",
    projectId = "ql-firestore-trial",
    storageBucket = "ql-firestore-trial.appspot.com",
    messagingSenderId = "401855810413")
  
   Firebase.initializeApp(config)
      
   val db = Firebase.firestore()
}