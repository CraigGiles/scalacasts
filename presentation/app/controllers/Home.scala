package controllers

import javax.inject._

import akka.actor.ActorSystem
import akka.util.Timeout
import com.gilesc.scalacasts.EchoActor
import play.api.mvc._
import akka.pattern.ask
import scala.concurrent.duration._
import play.api.libs.concurrent.Execution.Implicits.defaultContext

@Singleton
class Home @Inject() (system: ActorSystem) extends Controller {
  val echo = system.actorOf(EchoActor.props(), EchoActor.name)
  implicit val timeout = Timeout(1 second)

  def index = Action.async {
    (echo ? "Hey there echo...").mapTo[String].map { message =>
      Ok(views.html.index(message))
    }
  }

}

