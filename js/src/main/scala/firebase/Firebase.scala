 package firebase
 
 import scalajs.js
 import js.|
 import scala.scalajs.js.annotation._
 import auth.Auth
 import database.Database
 import messaging.Messaging
 import storage.Storage
 import firestore.Firestore
 
 @JSGlobal("firebase")
  @js.native
  object Firebase extends js.Object {
      
    var SDK_VERSION: String = js.native

    def app(name: String = ???): App = js.native

    var apps: js.Array[App | Null] = js.native

    def auth(app: App = ???): Auth = js.native

    def database(app: App = ???): Database = js.native

    def initializeApp(options: js.Dynamic | FirebaseConfig, name: String = ???): App = js.native

    def messaging(app: App = ???): Messaging = js.native

    def storage(app: App = ???): Storage = js.native
    
    def firestore(): Firestore = js.native
  }

  @JSExportAll
  case class FirebaseConfig(
    apiKey: String,
    authDomain: String,
    databaseURL: String,
    projectId: String,
    storageBucket: String,
    messagingSenderId: String
  )