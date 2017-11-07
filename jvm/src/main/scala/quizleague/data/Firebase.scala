package quizleague.data

import com.google.firebase._
import com.google.auth.oauth2.GoogleCredentials

object Firebase {
  val serviceAccount = this.getClass.getResourceAsStream("/quizleague/auth/firebase.json")

  val options = new FirebaseOptions.Builder()
  .setCredentials(GoogleCredentials.fromStream(serviceAccount))
  .setDatabaseUrl("https://quizleague-d02fe.firebaseio.com/")
  .build();

  FirebaseApp.initializeApp(options);

  
}