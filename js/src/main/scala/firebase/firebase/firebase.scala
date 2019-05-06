package firebase

import scala.scalajs.js
import js.annotation._
import js.| 
import auth._

@js.native
  trait FirebaseError extends js.Object {
    var code: String = js.native
    var message: String = js.native
    var name: String = js.native
    var stack: String = js.native
  }

  @js.native
  @JSGlobal("Promise")
  class Promise[T] extends Promise_Instance[T] {
  }

  @js.native
  @JSGlobal("Promise")
  object Promise extends js.Object {
    def all(values: js.Array[Promise[js.Any]]): Promise[js.Array[js.Any]] = js.native

    def reject(error: Error): Promise[js.Any] = js.native

    def resolve[T](value: T = ???): Promise[T] = js.native
  }

  @js.native
  @JSGlobal("Promise_Instance")
  class Promise_Instance[T] protected() extends Thenable[T] {
    def this(resolver: js.Function2[js.Function1[T, Unit], js.Function1[Error, Unit], Any]) = this()
  }

  @js.native
  trait Thenable[T] extends js.Object {
    def `catch`(onReject: js.Function1[Error, Any] = ???): js.Dynamic = js.native

    def `then`(onResolve: js.Function1[T, Any] = ???, onReject: js.Function1[Error, Any] = ???): Thenable[T] = js.native
  }

  @js.native
  trait User extends UserInfo {
    def delete(): Promise[js.Any] = js.native

    var emailVerified: Boolean = js.native

    def getToken(forceRefresh: Boolean = ???): Promise[js.Any] = js.native

    var isAnonymous: Boolean = js.native

    def link(credential: AuthCredential): Promise[js.Any] = js.native

    def linkWithPopup(provider: AuthProvider): Promise[js.Any] = js.native

    def linkWithRedirect(provider: AuthProvider): Promise[js.Any] = js.native

    var providerData: js.Array[UserInfo | Null] = js.native

    def reauthenticate(credential: AuthCredential): Promise[js.Any] = js.native

    var refreshToken: String = js.native

    def reload(): Promise[js.Any] = js.native

    def sendEmailVerification(): Promise[js.Any] = js.native

    def unlink(providerId: String): Promise[js.Any] = js.native

    def updateEmail(newEmail: String): Promise[js.Any] = js.native

    def updatePassword(newPassword: String): Promise[js.Any] = js.native

    def updateProfile(profile: js.Any): Promise[js.Any] = js.native
  }

  @js.native
  trait UserInfo extends js.Object {
    var displayName: String | Null = js.native
    var email: String | Null = js.native
    var photoURL: String | Null = js.native
    var providerId: String = js.native
    var uid: String = js.native
  }