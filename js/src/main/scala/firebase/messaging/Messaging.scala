package firebase.messaging

import scala.scalajs.js
import js.annotation._
import js.|


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