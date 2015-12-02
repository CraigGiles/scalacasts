package controllers

import javax.inject._

import akka.actor.ActorSystem
import akka.pattern.ask
import com.gilesc.scalacasts.bootstrap.{AkkaTimeoutSettings, ScalacastActors}
import com.gilesc.scalacasts.{Screencast, Receptionist, Video}
import models.ScreencastResource
import play.Logger
import play.api.Play.current
import play.api.data.Forms._
import play.api.data._
import play.api.i18n.Messages.Implicits._
import play.api.libs.concurrent.Execution.Implicits.defaultContext
import play.api.mvc._

@Singleton
class Home @Inject() (val system: ActorSystem) extends Controller with AkkaTimeoutSettings with ScalacastActors {

  val screencastResource = Form(
    mapping(
      "title" -> text,
      "description" -> text,
      "tags" -> text
    )(ScreencastResource.apply)(ScreencastResource.unapply)
  )

  def index = Action.async {
    val response = scalacastReceptionist ? Receptionist.FindVideoById(0)

    response.mapTo[Video].map { video =>
      Ok(views.html.index(screencastResource))
    }
  }

  def upload = Action(parse.multipartFormData) { implicit request =>
    import java.io.File

    screencastResource.bindFromRequest.fold(
      formWithErrors => {
        BadRequest(views.html.index(formWithErrors))
      },

      screencast => {
//        val contactId = Contact.save(contact)
//        Redirect(routes.Application.showContact(contactId)).flashing("success" -> "Contact saved!")

        request.body.file("screencast").map { video =>
          val filename = video.filename
          val contentType = video.contentType

          // TODO: config the path
          val path = s"/Users/gilesc/Desktop/tmp-$filename"
          video.ref.moveTo(new File(path), replace = true)

          scalacastReceptionist ! Receptionist.AddNewScreencast(path, screencast.title, screencast.description, screencast.tags)

//          Redirect(routes.Home.index()).flashing("success" -> "New Screencast Added")
          Ok("File Uploaded : " + screencast)
//        Redirect(routes.Application.showContact(contactId)).flashing("success" -> "Contact saved!")
        }.getOrElse {
          Redirect(routes.Home.index()).flashing("error" -> "Missing file")
        }

        Ok("File Uploaded : " + screencast)
      }
    )

  }

}

