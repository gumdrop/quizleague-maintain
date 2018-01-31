
import scala.scalajs.js
import js.annotation._
import js.|
import firebase.firebase.firestore.Firestore.{DocumentData,FirestoreErrorCode,DocumentChangeType,LogLevel,OrderByDirection,UpdateData,WhereFilterOp, Uint8Array}
import firebase.firebase.firestore.Firestore


package firebase {
  
  package firebase {

  @js.native
  trait FirebaseError extends js.Object {
    var code: String = js.native
    var message: String = js.native
    var name: String = js.native
    var stack: String = js.native
  }

  @js.native
  @JSGlobal("firebase.Promise")
  class Promise[T] extends Promise_Instance[T] {
  }

  @js.native
  @JSGlobal("firebase.Promise")
  object Promise extends js.Object {
    def all(values: js.Array[firebase.Promise[js.Any]]): firebase.Promise[js.Array[js.Any]] = js.native

    def reject(error: Error): firebase.Promise[js.Any] = js.native

    def resolve[T](value: T = ???): firebase.Promise[T] = js.native
  }

  @js.native
  @JSGlobal("firebase.Promise_Instance")
  class Promise_Instance[T] protected() extends firebase.Thenable[js.Any] {
    def this(resolver: js.Function2[js.Function1[T, Unit], js.Function1[Error, Unit], Any]) = this()
  }

  @js.native
  trait Thenable[T] extends js.Object {
    def `catch`(onReject: js.Function1[Error, Any] = ???): js.Dynamic = js.native

    def then(onResolve: js.Function1[T, Any] = ???, onReject: js.Function1[Error, Any] = ???): firebase.Thenable[js.Any] = js.native
  }

  @js.native
  trait User extends firebase.UserInfo {
    def delete(): firebase.Promise[js.Any] = js.native

    var emailVerified: Boolean = js.native

    def getToken(forceRefresh: Boolean = ???): firebase.Promise[js.Any] = js.native

    var isAnonymous: Boolean = js.native

    def link(credential: firebase.auth.AuthCredential): firebase.Promise[js.Any] = js.native

    def linkWithPopup(provider: firebase.auth.AuthProvider): firebase.Promise[js.Any] = js.native

    def linkWithRedirect(provider: firebase.auth.AuthProvider): firebase.Promise[js.Any] = js.native

    var providerData: js.Array[firebase.UserInfo | Null] = js.native

    def reauthenticate(credential: firebase.auth.AuthCredential): firebase.Promise[js.Any] = js.native

    var refreshToken: String = js.native

    def reload(): firebase.Promise[js.Any] = js.native

    def sendEmailVerification(): firebase.Promise[js.Any] = js.native

    def unlink(providerId: String): firebase.Promise[js.Any] = js.native

    def updateEmail(newEmail: String): firebase.Promise[js.Any] = js.native

    def updatePassword(newPassword: String): firebase.Promise[js.Any] = js.native

    def updateProfile(profile: js.Any): firebase.Promise[js.Any] = js.native
  }

  @js.native
  trait UserInfo extends js.Object {
    var displayName: String | Null = js.native
    var email: String | Null = js.native
    var photoURL: String | Null = js.native
    var providerId: String = js.native
    var uid: String = js.native
  }

  package app {

    @js.native
    trait App extends js.Object {
      def auth(): firebase.auth.Auth = js.native

      def database(): firebase.database.Database = js.native

      def delete(): firebase.Promise[js.Any] = js.native

      var name: String = js.native
      var options: Object = js.native

      def storage(): firebase.storage.Storage = js.native
    }

  }

  package auth {

    @js.native
    trait ActionCodeInfo extends js.Object {
    }

    @js.native
    trait Auth extends js.Object {
      var app: firebase.app.App = js.native

      def applyActionCode(code: String): firebase.Promise[js.Any] = js.native

      def checkActionCode(code: String): firebase.Promise[js.Any] = js.native

      def confirmPasswordReset(code: String, newPassword: String): firebase.Promise[js.Any] = js.native

      def createCustomToken(uid: String, developerClaims: Object | Null = ???): String = js.native

      def createUserWithEmailAndPassword(email: String, password: String): firebase.Promise[js.Any] = js.native

      var currentUser: firebase.User | Null = js.native

      def fetchProvidersForEmail(email: String): firebase.Promise[js.Any] = js.native

      def getRedirectResult(): firebase.Promise[js.Any] = js.native

      def onAuthStateChanged(nextOrObserver: Object, error: js.Function1[firebase.auth.Error, Any] = ???, completed: js.Function0[Any] = ???): js.Function0[Any] = js.native

      def sendPasswordResetEmail(email: String): firebase.Promise[js.Any] = js.native

      def signInAnonymously(): firebase.Promise[js.Any] = js.native

      def signInWithCredential(credential: firebase.auth.AuthCredential): firebase.Promise[js.Any] = js.native

      def signInWithCustomToken(token: String): firebase.Promise[js.Any] = js.native

      def signInWithEmailAndPassword(email: String, password: String): firebase.Promise[js.Any] = js.native

      def signInWithPopup(provider: firebase.auth.AuthProvider): firebase.Promise[js.Any] = js.native

      def signInWithRedirect(provider: firebase.auth.AuthProvider): firebase.Promise[js.Any] = js.native

      def signOut(): firebase.Promise[js.Any] = js.native

      def verifyIdToken(idToken: String): firebase.Promise[js.Any] = js.native

      def verifyPasswordResetCode(code: String): firebase.Promise[js.Any] = js.native
    }

    @js.native
    trait AuthCredential extends js.Object {
      var provider: String = js.native
    }

    @js.native
    trait AuthProvider extends js.Object {
      var providerId: String = js.native
    }

    @js.native
    @JSGlobal("firebase.auth.EmailAuthProvider")
    class EmailAuthProvider extends EmailAuthProvider_Instance {
    }

    @js.native
    @JSGlobal("firebase.auth.EmailAuthProvider")
    object EmailAuthProvider extends js.Object {
      var PROVIDER_ID: String = js.native

      def credential(email: String, password: String): firebase.auth.AuthCredential = js.native
    }

    @js.native
    @JSGlobal("firebase.auth.EmailAuthProvider_Instance")
    class EmailAuthProvider_Instance extends firebase.auth.AuthProvider {
    }

    @js.native
    trait Error extends js.Object {
      var code: String = js.native
      var message: String = js.native
    }

    @js.native
    @JSGlobal("firebase.auth.FacebookAuthProvider")
    class FacebookAuthProvider extends FacebookAuthProvider_Instance {
    }

    @js.native
    @JSGlobal("firebase.auth.FacebookAuthProvider")
    object FacebookAuthProvider extends js.Object {
      var PROVIDER_ID: String = js.native

      def credential(token: String): firebase.auth.AuthCredential = js.native
    }

    @js.native
    @JSGlobal("firebase.auth.FacebookAuthProvider_Instance")
    class FacebookAuthProvider_Instance extends firebase.auth.AuthProvider {
      def addScope(scope: String): js.Dynamic = js.native


      def setCustomParameters(customOAuthParameters: Object): js.Dynamic = js.native
    }

    @js.native
    @JSGlobal("firebase.auth.GithubAuthProvider")
    class GithubAuthProvider extends GithubAuthProvider_Instance {
    }

    @js.native
    @JSGlobal("firebase.auth.GithubAuthProvider")
    object GithubAuthProvider extends js.Object {
      var PROVIDER_ID: String = js.native

      def credential(token: String): firebase.auth.AuthCredential = js.native
    }

    @js.native
    @JSGlobal("firebase.auth.GithubAuthProvider_Instance")
    class GithubAuthProvider_Instance extends firebase.auth.AuthProvider {
      def addScope(scope: String): js.Dynamic = js.native


      def setCustomParameters(customOAuthParameters: Object): js.Dynamic = js.native
    }

    @js.native
    @JSGlobal("firebase.auth.GoogleAuthProvider")
    class GoogleAuthProvider extends GoogleAuthProvider_Instance {
    }

    @js.native
    @JSGlobal("firebase.auth.GoogleAuthProvider")
    object GoogleAuthProvider extends js.Object {
      var PROVIDER_ID: String = js.native

      def credential(idToken: String | Null = ???, accessToken: String | Null = ???): firebase.auth.AuthCredential = js.native
    }

    @js.native
    @JSGlobal("firebase.auth.GoogleAuthProvider_Instance")
    class GoogleAuthProvider_Instance extends firebase.auth.AuthProvider {
      def addScope(scope: String): js.Dynamic = js.native


      def setCustomParameters(customOAuthParameters: Object): js.Dynamic = js.native
    }

    @js.native
    @JSGlobal("firebase.auth.TwitterAuthProvider")
    class TwitterAuthProvider extends TwitterAuthProvider_Instance {
    }

    @js.native
    @JSGlobal("firebase.auth.TwitterAuthProvider")
    object TwitterAuthProvider extends js.Object {
      var PROVIDER_ID: String = js.native

      def credential(token: String, secret: String): firebase.auth.AuthCredential = js.native
    }

    @js.native
    @JSGlobal("firebase.auth.TwitterAuthProvider_Instance")
    class TwitterAuthProvider_Instance extends firebase.auth.AuthProvider {

      def setCustomParameters(customOAuthParameters: Object): js.Dynamic = js.native
    }

    @JSGlobal("firebase.auth")
    @js.native
    object Auth extends js.Object {
      type UserCredential = js.Any
    }

  }

  package database {

    @js.native
    trait DataSnapshot extends js.Object {
      def child(path: String): firebase.database.DataSnapshot = js.native

      def exists(): Boolean = js.native

      def exportVal(): js.Dynamic = js.native

      def forEach(action: js.Function1[firebase.database.DataSnapshot, Boolean]): Boolean = js.native

      def getPriority(): String | Double | Null = js.native

      def hasChild(path: String): Boolean = js.native

      def hasChildren(): Boolean = js.native

      var key: String | Null = js.native

      def numChildren(): Double = js.native

      var ref: firebase.database.Reference = js.native

      def `val`(): js.Dynamic = js.native
    }

    @js.native
    trait Database extends js.Object {
      var app: firebase.app.App = js.native

      def goOffline(): js.Dynamic = js.native

      def goOnline(): js.Dynamic = js.native

      def ref(path: String = ???): firebase.database.Reference = js.native

      def refFromURL(url: String): firebase.database.Reference = js.native
    }

    @js.native
    trait OnDisconnect extends js.Object {
      def cancel(onComplete: js.Function1[Error | Null, Any] = ???): firebase.Promise[js.Any] = js.native

      def remove(onComplete: js.Function1[Error | Null, Any] = ???): firebase.Promise[js.Any] = js.native

      def set(value: js.Any, onComplete: js.Function1[Error | Null, Any] = ???): firebase.Promise[js.Any] = js.native

      def setWithPriority(value: js.Any, priority: Double | String | Null, onComplete: js.Function1[Error | Null, Any] = ???): firebase.Promise[js.Any] = js.native

      def update(values: Object, onComplete: js.Function1[Error | Null, Any] = ???): firebase.Promise[js.Any] = js.native
    }

    @js.native
    trait Query extends js.Object {
      def endAt(value: Double | String | Boolean | Null, key: String = ???): firebase.database.Query = js.native

      def equalTo(value: Double | String | Boolean | Null, key: String = ???): firebase.database.Query = js.native

      def isEqual(other: firebase.database.Query | Null): Boolean = js.native

      def limitToFirst(limit: Double): firebase.database.Query = js.native

      def limitToLast(limit: Double): firebase.database.Query = js.native

      def off(eventType: String = ???, callback: js.Function2[firebase.database.DataSnapshot, String | Null, Any] = ???, context: Object | Null = ???): js.Dynamic = js.native

      def on(eventType: String, callback: js.Function2[firebase.database.DataSnapshot, String | Null, Any], cancelCallbackOrContext: Object | Null = ???, context: Object | Null = ???): js.Function2[firebase.database.DataSnapshot, String | Null, Any] = js.native

      def once(eventType: String, successCallback: js.Function2[firebase.database.DataSnapshot, String | Null, Any] = ???, failureCallbackOrContext: Object | Null = ???, context: Object | Null = ???): firebase.Promise[js.Any] = js.native

      def orderByChild(path: String): firebase.database.Query = js.native

      def orderByKey(): firebase.database.Query = js.native

      def orderByPriority(): firebase.database.Query = js.native

      def orderByValue(): firebase.database.Query = js.native

      var ref: firebase.database.Reference = js.native

      def startAt(value: Double | String | Boolean | Null, key: String = ???): firebase.database.Query = js.native

      override def toString(): String = js.native
    }

    @js.native
    trait Reference extends firebase.database.Query {
      def child(path: String): firebase.database.Reference = js.native

      var key: String | Null = js.native

      def onDisconnect(): firebase.database.OnDisconnect = js.native

      var parent: firebase.database.Reference | Null = js.native

      def push(value: js.Any = ???, onComplete: js.Function1[Error | Null, Any] = ???): firebase.database.ThenableReference = js.native

      def remove(onComplete: js.Function1[Error | Null, Any] = ???): firebase.Promise[js.Any] = js.native

      var root: firebase.database.Reference = js.native

      def set(value: js.Any, onComplete: js.Function1[Error | Null, Any] = ???): firebase.Promise[js.Any] = js.native

      def setPriority(priority: String | Double | Null, onComplete: js.Function1[Error | Null, Any]): firebase.Promise[js.Any] = js.native

      def setWithPriority(newVal: js.Any, newPriority: String | Double | Null, onComplete: js.Function1[Error | Null, Any] = ???): firebase.Promise[js.Any] = js.native

      def transaction(transactionUpdate: js.Function1[js.Any, Any], onComplete: js.Function3[Error | Null, Boolean, firebase.database.DataSnapshot | Null, Any] = ???, applyLocally: Boolean = ???): firebase.Promise[js.Any] = js.native

      def update(values: Object, onComplete: js.Function1[Error | Null, Any] = ???): firebase.Promise[js.Any] = js.native
    }

    @js.native
    trait ThenableReference extends firebase.database.Reference with firebase.Thenable[js.Any] {
    }

    package ServerValue {

      @JSGlobal("firebase.database.ServerValue")
      @js.native
      object ServerValue extends js.Object {
        var TIMESTAMP: Object = js.native
      }

    }

    @JSGlobal("firebase.database")
    @js.native
    object Database extends js.Object {
      def enableLogging(enabled: Boolean = ???, persistent: Boolean = ???): js.Dynamic = js.native
    }

  }

  package messaging {

    @js.native
    trait Messaging extends js.Object {
      def deleteToken(token: String): firebase.Promise[js.Any] | Null = js.native

      def getToken(): firebase.Promise[js.Any] | Null = js.native

      def onMessage(nextOrObserver: Object): js.Function0[Any] = js.native

      def onTokenRefresh(nextOrObserver: Object): js.Function0[Any] = js.native

      def requestPermission(): firebase.Promise[js.Any] | Null = js.native

      def setBackgroundMessageHandler(callback: js.Function1[Object, Any]): js.Dynamic = js.native

      def useServiceWorker(registration: js.Any): js.Dynamic = js.native
    }

  }

  package storage {

    @js.native
    trait FullMetadata extends firebase.storage.UploadMetadata {
      var bucket: String = js.native
      var downloadURLs: js.Array[String] = js.native
      var fullPath: String = js.native
      var generation: String = js.native
      var metageneration: String = js.native
      var name: String = js.native
      var size: Double = js.native
      var timeCreated: String = js.native
      var updated: String = js.native
    }

    @js.native
    trait Reference extends js.Object {
      var bucket: String = js.native

      def child(path: String): firebase.storage.Reference = js.native

      def delete(): firebase.Promise[js.Any] = js.native

      var fullPath: String = js.native

      def getDownloadURL(): firebase.Promise[js.Any] = js.native

      def getMetadata(): firebase.Promise[js.Any] = js.native

      var name: String = js.native
      var parent: firebase.storage.Reference | Null = js.native

      def put(data: js.Any, metadata: firebase.storage.UploadMetadata = ???): firebase.storage.UploadTask = js.native

      def putString(data: String, format: firebase.storage.Storage.StringFormat = ???, metadata: firebase.storage.UploadMetadata = ???): firebase.storage.UploadTask = js.native

      var root: firebase.storage.Reference = js.native
      var storage: firebase.storage.Storage = js.native

      override def toString(): String = js.native

      def updateMetadata(metadata: firebase.storage.SettableMetadata): firebase.Promise[js.Any] = js.native
    }

    @js.native
    trait SettableMetadata extends js.Object {
      var cacheControl: String | Null = js.native
      var contentDisposition: String | Null = js.native
      var contentEncoding: String | Null = js.native
      var contentLanguage: String | Null = js.native
      var contentType: String | Null = js.native
      var customMetadata: js.Dictionary[String] | Null = js.native
    }

    @js.native
    trait Storage extends js.Object {
      var app: firebase.app.App = js.native
      var maxOperationRetryTime: Double = js.native
      var maxUploadRetryTime: Double = js.native

      def ref(path: String = ???): firebase.storage.Reference = js.native

      def refFromURL(url: String): firebase.storage.Reference = js.native

      def setMaxOperationRetryTime(time: Double): js.Dynamic = js.native

      def setMaxUploadRetryTime(time: Double): js.Dynamic = js.native
    }

    @js.native
    @JSGlobal("firebase.storage.StringFormat")
    object StringFormat extends js.Object {
      var BASE64: firebase.storage.Storage.StringFormat = js.native
      var BASE64URL: firebase.storage.Storage.StringFormat = js.native
      var DATA_URL: firebase.storage.Storage.StringFormat = js.native
      var RAW: firebase.storage.Storage.StringFormat = js.native
    }

    @js.native
    @JSGlobal("firebase.storage.TaskEvent")
    object TaskEvent extends js.Object {
      var STATE_CHANGED: firebase.storage.Storage.TaskEvent = js.native
    }

    @js.native
    @JSGlobal("firebase.storage.TaskState")
    object TaskState extends js.Object {
      var CANCELED: firebase.storage.Storage.TaskState = js.native
      var ERROR: firebase.storage.Storage.TaskState = js.native
      var PAUSED: firebase.storage.Storage.TaskState = js.native
      var RUNNING: firebase.storage.Storage.TaskState = js.native
      var SUCCESS: firebase.storage.Storage.TaskState = js.native
    }

    @js.native
    trait UploadMetadata extends firebase.storage.SettableMetadata {
      var md5Hash: String | Null = js.native
    }

    @js.native
    trait UploadTask extends js.Object {
      def cancel(): Boolean = js.native

      def `catch`(onRejected: js.Function1[Error, Any]): firebase.Promise[js.Any] = js.native

      def on(event: firebase.storage.Storage.TaskEvent, nextOrObserver: Null | Object = ???, error: js.Function1[Error, Any] | Null = ???, complete: js.Function0[Any] | Null = ???): js.Function = js.native

      def pause(): Boolean = js.native

      def resume(): Boolean = js.native

      var snapshot: firebase.storage.UploadTaskSnapshot = js.native

      def then(onFulfilled: js.Function1[firebase.storage.UploadTaskSnapshot, Any] | Null = ???, onRejected: js.Function1[Error, Any] | Null = ???): firebase.Promise[js.Any] = js.native
    }

    @js.native
    trait UploadTaskSnapshot extends js.Object {
      var bytesTransferred: Double = js.native
      var downloadURL: String | Null = js.native
      var metadata: firebase.storage.FullMetadata = js.native
      var ref: firebase.storage.Reference = js.native
      var state: firebase.storage.Storage.TaskState = js.native
      var task: firebase.storage.UploadTask = js.native
      var totalBytes: Double = js.native
    }

    @JSGlobal("firebase.storage")
    @js.native
    object Storage extends js.Object {
      type StringFormat = String
      type TaskEvent = String
      type TaskState = String
    }

  }

  @JSGlobal("firebase")
  @js.native
  object Firebase extends js.Object {
     
    var SDK_VERSION: String = js.native

    def app(name: String = ???): firebase.app.App = js.native

    var apps: js.Array[firebase.app.App | Null] = js.native

    def auth(app: firebase.app.App = ???): firebase.auth.Auth = js.native

    def database(app: firebase.app.App = ???): firebase.database.Database = js.native

    def initializeApp(options: js.Dynamic | FirebaseConfig, name: String = ???): firebase.app.App = js.native

    def messaging(app: firebase.app.App = ???): firebase.messaging.Messaging = js.native

    def storage(app: firebase.app.App = ???): firebase.storage.Storage = js.native
    
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


package firestore {
    

@js.native
trait Settings extends js.Object {
  var host: String = js.native
  var ssl: Boolean = js.native
}

@js.native
@JSGlobal("firebase.firestore.Firestore")
class Firestore extends js.Object {
  def settings(settings: Settings): Unit = js.native
  def enablePersistence(): Promise[Unit] = js.native
  def collection(collectionPath: String): CollectionReference = js.native
  def doc(documentPath: String): DocumentReference = js.native
  def runTransaction[T](updateFunction: js.Function1[Transaction, Promise[T]]): Promise[T] = js.native
  def batch(): WriteBatch = js.native
  var app: firebase.app.App = js.native
  var INTERNAL: js.Any = js.native
}

@js.native
@JSGlobal("firebase.firestore.GeoPoint")
class GeoPoint protected () extends js.Object {
  def this(latitude: Double, longitude: Double) = this()
  def latitude: Double = js.native
  def longitude: Double = js.native
}

@js.native
@JSGlobal("firebase.firestore.Blob")
class Blob extends js.Object {
  def toBase64(): String = js.native
  def toUint8Array(): Uint8Array = js.native
}

@js.native
@JSGlobal("firebase.firestore.Blob")
object Blob extends js.Object {
  def fromBase64String(base64: String): Blob = js.native
  def fromUint8Array(array: Uint8Array): Blob = js.native
}

@js.native
@JSGlobal("firebase.firestore.Transaction")
class Transaction extends js.Object {
  def get(documentRef: DocumentReference): Promise[DocumentSnapshot] = js.native
  def set(documentRef: DocumentReference, data: DocumentData, options: SetOptions = ???): Transaction = js.native
  def update(documentRef: DocumentReference, data: UpdateData): Transaction = js.native
  def update(documentRef: DocumentReference, field: String | FieldPath, value: js.Any, moreFieldsAndValues: js.Any*): Transaction = js.native
  def delete(documentRef: DocumentReference): Transaction = js.native
}

@js.native
@JSGlobal("firebase.firestore.WriteBatch")
class WriteBatch extends js.Object {
  def set(documentRef: DocumentReference, data: DocumentData, options: SetOptions = ???): WriteBatch = js.native
  def update(documentRef: DocumentReference, data: UpdateData): WriteBatch = js.native
  def update(documentRef: DocumentReference, field: String | FieldPath, value: js.Any, moreFieldsAndValues: js.Any*): WriteBatch = js.native
  def delete(documentRef: DocumentReference): WriteBatch = js.native
  def commit(): Promise[Unit] = js.native
}

@js.native
trait DocumentListenOptions extends js.Object {
  def includeMetadataChanges: Boolean = js.native
}

@js.native
trait SetOptions extends js.Object {
  def merge: Boolean = js.native
}

@js.native
@JSGlobal("firebase.firestore.DocumentReference")
class DocumentReference extends js.Object {
  def id: String = js.native
  def firestore: Firestore = js.native
  def parent: CollectionReference = js.native
  def path: String = js.native
  def collection(collectionPath: String): CollectionReference = js.native
  def isEqual(other: DocumentReference): Boolean = js.native
  def set(data: DocumentData, options: SetOptions = ???): Promise[Unit] = js.native
  def update(data: UpdateData): Promise[Unit] = js.native
  def update(field: String | FieldPath, value: js.Any, moreFieldsAndValues: js.Any*): Promise[Unit] = js.native
  def delete(): Promise[Unit] = js.native
  def get(): Promise[DocumentSnapshot] = js.native
  def onSnapshot(observer: js.Any): js.Function0[Unit] = js.native
  def onSnapshot(options: DocumentListenOptions, observer: js.Any): js.Function0[Unit] = js.native
  def onSnapshot(onNext: js.Function1[DocumentSnapshot, Unit], onError: js.Function1[Error, Unit], onCompletion: js.Function0[Unit]): js.Function0[Unit] = js.native
  def onSnapshot(options: DocumentListenOptions, onNext: js.Function1[DocumentSnapshot, Unit], onError: js.Function1[Error, Unit] = ???, onCompletion: js.Function0[Unit] = ???): js.Function0[Unit] = js.native
}

@js.native
trait SnapshotMetadata extends js.Object {
  def hasPendingWrites: Boolean = js.native
  def fromCache: Boolean = js.native
}

@js.native
@JSGlobal("firebase.firestore.DocumentSnapshot")
class DocumentSnapshot extends js.Object {
  def exists: Boolean = js.native
  def ref: DocumentReference = js.native
  def id: String = js.native
  def metadata: SnapshotMetadata = js.native
  def data(): DocumentData = js.native
  def get(fieldPath: String | FieldPath): js.Dynamic = js.native
}

@js.native
trait QueryListenOptions extends js.Object {
  def includeQueryMetadataChanges: Boolean = js.native
  def includeDocumentMetadataChanges: Boolean = js.native
}

@js.native
@JSGlobal("firebase.firestore.Query")
class Query extends js.Object {
  def firestore: Firestore = js.native
  def where(fieldPath: String | FieldPath, opStr: WhereFilterOp, value: js.Any): Query = js.native
  def orderBy(fieldPath: String | FieldPath, directionStr: OrderByDirection = ???): Query = js.native
  def limit(limit: Double): Query = js.native
  def startAt(snapshot: DocumentSnapshot): Query = js.native
  def startAt(fieldValues: js.Any*): Query = js.native
  def startAfter(snapshot: DocumentSnapshot): Query = js.native
  def startAfter(fieldValues: js.Any*): Query = js.native
  def endBefore(snapshot: DocumentSnapshot): Query = js.native
  def endBefore(fieldValues: js.Any*): Query = js.native
  def endAt(snapshot: DocumentSnapshot): Query = js.native
  def endAt(fieldValues: js.Any*): Query = js.native
  def isEqual(other: Query): Boolean = js.native
  def get(): Promise[QuerySnapshot] = js.native
  def onSnapshot(observer: js.Any): js.Function0[Unit] = js.native
  def onSnapshot(options: QueryListenOptions, observer: js.Any): js.Function0[Unit] = js.native
  def onSnapshot(onNext: js.Function1[QuerySnapshot, Unit], onError: js.Function1[Error, Unit], onCompletion: js.Function0[Unit]): js.Function0[Unit] = js.native
  def onSnapshot(options: QueryListenOptions, onNext: js.Function1[QuerySnapshot, Unit], onError: js.Function1[Error, Unit] = ???, onCompletion: js.Function0[Unit] = ???): js.Function0[Unit] = js.native
}

@js.native
@JSGlobal("firebase.firestore.QuerySnapshot")
class QuerySnapshot extends js.Object {
  def query: Query = js.native
  def metadata: SnapshotMetadata = js.native
  def docChanges: js.Array[DocumentChange] = js.native
  def docs: js.Array[DocumentSnapshot] = js.native
  def size: Double = js.native
  def empty: Boolean = js.native
  def forEach(callback: js.Function1[DocumentSnapshot, Unit], thisArg: js.Any = ???): Unit = js.native
}

@js.native
trait DocumentChange extends js.Object {
  def `type`: DocumentChangeType = js.native
  def doc: DocumentSnapshot = js.native
  def oldIndex: Double = js.native
  def newIndex: Double = js.native
}

@js.native
@JSGlobal("firebase.firestore.CollectionReference")
class CollectionReference extends Query {
  def id: String = js.native
  def parent: DocumentReference | Null = js.native
  def path: String = js.native
  def doc(documentPath: String = ???): DocumentReference = js.native
  def add(data: DocumentData): Promise[DocumentReference] = js.native
}

@js.native
@JSGlobal("firebase.firestore.FieldValue")
class FieldValue extends js.Object {
}

@js.native
@JSGlobal("firebase.firestore.FieldValue")
object FieldValue extends js.Object {
  def serverTimestamp(): FieldValue = js.native
  def delete(): FieldValue = js.native
}

@js.native
@JSGlobal("firebase.firestore.FieldPath")
class FieldPath protected () extends js.Object {
  def this(fieldNames: String*) = this()
}

@js.native
@JSGlobal("firebase.firestore.FieldPath")
object FieldPath extends js.Object {
  def documentId(): FieldPath = js.native
}

@js.native
trait FirestoreError extends js.Object {
  var code: FirestoreErrorCode = js.native
  var message: String = js.native
  var name: String = js.native
  var stack: String = js.native
}

@js.native
@JSGlobal("firebase.firestore")
object Firestore extends js.Object {
  type DocumentData = js.Dictionary[js.Any]
  type UpdateData = js.Dictionary[js.Any]
  type LogLevel = String
  type FirestoreErrorCode = String
  def setLogLevel(logLevel: LogLevel): Unit = js.native
  type OrderByDirection = String
  type WhereFilterOp = String
  type DocumentChangeType = String
  type Uint8Array = js.Array[Integer]
}

}

}

}

