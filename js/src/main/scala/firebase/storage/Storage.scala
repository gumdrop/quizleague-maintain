package firebase.storage

import scala.scalajs.js
import js.annotation._
import js.|
import firebase.Promise

//import firebase.firebase

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

      def delete(): Promise[js.Any] = js.native

      var fullPath: String = js.native

      def getDownloadURL(): Promise[js.Any] = js.native

      def getMetadata(): Promise[js.Any] = js.native

      var name: String = js.native
      var parent: firebase.storage.Reference | Null = js.native

      def put(data: js.Any, metadata: firebase.storage.UploadMetadata = ???): firebase.storage.UploadTask = js.native

      def putString(data: String, format: firebase.storage.Storage.StringFormat = ???, metadata: firebase.storage.UploadMetadata = ???): firebase.storage.UploadTask = js.native

      var root: firebase.storage.Reference = js.native
      var storage: firebase.storage.Storage = js.native

      override def toString(): String = js.native

      def updateMetadata(metadata: firebase.storage.SettableMetadata): Promise[js.Any] = js.native
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

      def `catch`(onRejected: js.Function1[Error, Any]): Promise[js.Any] = js.native

      def on(event: firebase.storage.Storage.TaskEvent, nextOrObserver: Null | Object = ???, error: js.Function1[Error, Any] | Null = ???, complete: js.Function0[Any] | Null = ???): js.Function = js.native

      def pause(): Boolean = js.native

      def resume(): Boolean = js.native

      var snapshot: firebase.storage.UploadTaskSnapshot = js.native

      def `then`(onFulfilled: js.Function1[firebase.storage.UploadTaskSnapshot, Any] | Null = ???, onRejected: js.Function1[Error, Any] | Null = ???): Promise[js.Any] = js.native
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