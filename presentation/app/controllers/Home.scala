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

import com.gilesc.scalacasts.ContentType
import com.gilesc.scalacasts.Title
import com.gilesc.scalacasts.Description
import com.gilesc.scalacasts.Tag
import com.gilesc.scalacasts.screencast.Scalacasts
import com.gilesc.scalacasts.screencast.ScreencastContext

import scala.concurrent.ExecutionContext.Implicits.global

class Home extends Controller {
  val scalacasts = new Scalacasts()
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
    // Redirect(routes.Home.index()).flashing("success" -> "Screencast Added")

    import java.io.File
    val config = ConfigFactory.load()

    screencastResource.bindFromRequest.fold(
      formWithErrors => {
        BadRequest(views.html.home.contact(formWithErrors))
      },

      screencast => request.body.file("screencast").map { video =>
        val filename = video.filename
        val contentType = video.contentType.getOrElse("N/A")
        val path = s"${config.getString("scalacasts.videos.folder")}/tmp-$filename"
        val title = screencast.title
        val description = screencast.description
        val tags = screencast.tags.split(",").map(t => Tag(t.trim)).toSet

        video.ref.moveTo(new File(path), replace = true)

        val cxt = ScreencastContext(
          path,
          ContentType(contentType),
          Title(title),
          Description(description),
          tags)

        val result = scalacasts.add(cxt)

        result.mapTo[Int].map { message =>
          Logger.info("LOGGER: SUCCESSFUL " + message)
        }

        Redirect(routes.Home.index()).flashing("success" -> "Screencast Added")
      }.getOrElse {
        Redirect(routes.Home.index()).flashing("error" -> "Missing file")
      })
  }

}

