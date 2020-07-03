package firebase.firestore

import scala.scalajs.js
import js.annotation._
import js.|
import firebase.Promise
import firebase.firestore.Firestore.{DocumentData,FirestoreErrorCode,DocumentChangeType,LogLevel,OrderByDirection,UpdateData,WhereFilterOp, Uint8Array}

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
  def enablePersistence(config:js.Object): Promise[Unit] = js.native
  def collection(collectionPath: String): CollectionReference = js.native
  def collectionGroup(collectionName: String): Query = js.native
  def doc(documentPath: String): DocumentReference = js.native
  def runTransaction[T](updateFunction: js.Function1[Transaction, Promise[T]]): Promise[T] = js.native
  def batch(): WriteBatch = js.native
  var app: firebase.app.App = js.native
  var INTERNAL: js.Any = js.native
  val settings:Settings = js.native
  val hostname:String = js.native
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