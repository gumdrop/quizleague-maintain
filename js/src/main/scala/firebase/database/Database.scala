package firebase.database

import scala.scalajs.js
import js.annotation._
import js.|
import firebase.Promise
import firebase.Thenable

//import firebase.firebase

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
      def cancel(onComplete: js.Function1[Error | Null, Any] = ???): Promise[js.Any] = js.native

      def remove(onComplete: js.Function1[Error | Null, Any] = ???): Promise[js.Any] = js.native

      def set(value: js.Any, onComplete: js.Function1[Error | Null, Any] = ???): Promise[js.Any] = js.native

      def setWithPriority(value: js.Any, priority: Double | String | Null, onComplete: js.Function1[Error | Null, Any] = ???): Promise[js.Any] = js.native

      def update(values: Object, onComplete: js.Function1[Error | Null, Any] = ???): Promise[js.Any] = js.native
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

      def once(eventType: String, successCallback: js.Function2[firebase.database.DataSnapshot, String | Null, Any] = ???, failureCallbackOrContext: Object | Null = ???, context: Object | Null = ???): Promise[js.Any] = js.native

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

      def remove(onComplete: js.Function1[Error | Null, Any] = ???): Promise[js.Any] = js.native

      var root: firebase.database.Reference = js.native

      def set(value: js.Any, onComplete: js.Function1[Error | Null, Any] = ???): Promise[js.Any] = js.native

      def setPriority(priority: String | Double | Null, onComplete: js.Function1[Error | Null, Any]): Promise[js.Any] = js.native

      def setWithPriority(newVal: js.Any, newPriority: String | Double | Null, onComplete: js.Function1[Error | Null, Any] = ???): Promise[js.Any] = js.native

      def transaction(transactionUpdate: js.Function1[js.Any, Any], onComplete: js.Function3[Error | Null, Boolean, firebase.database.DataSnapshot | Null, Any] = ???, applyLocally: Boolean = ???): Promise[js.Any] = js.native

      def update(values: Object, onComplete: js.Function1[Error | Null, Any] = ???): Promise[js.Any] = js.native
    }

    @js.native
    trait ThenableReference extends firebase.database.Reference with Thenable[js.Any] {
    }

      @JSGlobal("firebase.database.ServerValue")
      @js.native
      object ServerValue extends js.Object {
        var TIMESTAMP: Object = js.native
      }



    @JSGlobal("firebase.database")
    @js.native
    object Database extends js.Object {
      def enableLogging(enabled: Boolean = ???, persistent: Boolean = ???): js.Dynamic = js.native
    }

