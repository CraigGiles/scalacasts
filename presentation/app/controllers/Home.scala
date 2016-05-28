package controllers

import javax.inject._

import com.typesafe.config.ConfigFactory
import models.ScreencastResource
import play.api.Logger
import play.api.Play.current
import play.api.data.Forms._
import play.api.data._
import play.api.i18n.Messages.Implicits._
import play.api.mvc._

class Home extends Controller {
  val screencastResource = Form(
    mapping(
      "title" -> text,
      "description" -> text,
      "tags" -> text)(ScreencastResource.apply)(ScreencastResource.unapply))

  def index = Action {
    Ok(views.html.home.index())
  }

  def about = Action {
    Ok(views.html.home.about())
  }

  def contact = Action {
    Ok(views.html.home.contact(screencastResource))
  }

  def upload = Action(parse.multipartFormData) { implicit request =>
    Redirect(routes.Home.index()).flashing("success" -> "Screencast Added")

    // import java.io.File
    // val config = ConfigFactory.load()

    // screencastResource.bindFromRequest.fold(
    //   formWithErrors => {
    //     BadRequest(views.html.home.contact(formWithErrors))
    //   },

    //   screencast => request.body.file("screencast").map { video =>
    //     val filename = video.filename
    //     val contentType = video.contentType.getOrElse("N/A")
    //     val path = s"${config.getString("scalacasts.videos.folder")}/tmp-$filename"

    //     video.ref.moveTo(new File(path), replace = true)

    //     val result = scalacastReceptionist ?
    //       Receptionist.AddNewScreencast(path, contentType, screencast.title, screencast.description, screencast.tags)

    //     result.mapTo[Receptionist.Successful].map { message =>
    //       Logger.info("LOGGER: SUCCESSFUL " + message)
    //     }

    //     Redirect(routes.Home.index()).flashing("success" -> "Screencast Added")
    //   }.getOrElse {
    //     Redirect(routes.Home.index()).flashing("error" -> "Missing file")
    //   })
  }

}

