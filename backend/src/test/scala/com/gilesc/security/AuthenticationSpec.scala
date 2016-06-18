//package com.gilesc.security
//
//import com.gilesc.scalacasts.User
//import com.gilesc.scalacasts.dataaccess.MySqlDatabaseDriver
//import com.gilesc.scalacasts.dataaccess.repository.UserRepository
//import com.gilesc.scalacasts.security.{LoginInfo, Authentication}
//import com.gilesc.scalacasts.testing.TestCase
//
//import scala.concurrent.Future
//import scala.concurrent.ExecutionContext.Implicits.global
//
//class AuthenticationSpec extends TestCase {
//  lazy val myUser = User(42, "username", "email", "passwordhash")
//  val mockUserRepo = new UserRepository(MySqlDatabaseDriver) {
//    override def findByUsername(username: String): Future[Option[User]] =
//      Future(Some(myUser))
//  }
//
//  "Authenitcating a user" should {
//    "Allow the user with the proper password to be authenticated" in {
//      val auth = new Authentication {
//        override val userRepo = mockUserRepo
//      }
//
//      val result = auth.login(LoginInfo("username", "passwordhash"))
//
//      whenReady(result) { user =>
//        user.id should be(myUser.id)
//        user.username should be(myUser.username)
//      }
//    }
//  }
//}
