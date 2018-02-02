package quizleague.web.store

import scalajs.js.Dynamic.literal
import firebase.firebase._

object Firestore {
    val config = literal(      
//     apiKey = "AIzaSyAzKh8FB6M7DEF8bTsjNOnJWTRKzimUaWk",
//    authDomain ="ql-firestore-trial.firebaseapp.com",
//    databaseURL = "https://ql-firestore-trial.firebaseio.com",
//    projectId = "ql-firestore-trial",
//    storageBucket = "ql-firestore-trial.appspot.com",
//    messagingSenderId = "401855810413"
    apiKey= "AIzaSyCTnCW1euWGpRohoEBESIdNEASM7rQ5gkY",
    authDomain= "ql-firestore-2.firebaseapp.com",
    databaseURL= "https=//ql-firestore-2.firebaseio.com",
    projectId= "ql-firestore-2",
    storageBucket= "ql-firestore-2.appspot.com",
    messagingSenderId= "659931577179"
)
  
   Firebase.initializeApp(config)
      
   val db = Firebase.firestore()
}