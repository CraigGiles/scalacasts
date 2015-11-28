package controllers

import javax.inject._

import akka.actor.ActorSystem
import akka.pattern.ask
import com.gilesc.scalacasts.bootstrap.{AkkaTimeoutSettings, ScalacastActors}
import com.gilesc.scalacasts.{Receptionist, Video}
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.mvc._

@Singleton
class Home @Inject() (val system: ActorSystem) extends Controller with AkkaTimeoutSettings with ScalacastActors {

  def index = Action.async {
    val response = scalacastReceptionist ? Receptionist.FindVideoByTitle("What title are you?")

    response.mapTo[Video].map { video =>
      Ok(views.html.index(video.toString))
    }
  }

}

