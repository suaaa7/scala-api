package http

import akka.http.scaladsl.marshallers.sprayjson.SprayJsonSupport
import akka.http.scaladsl.model.HttpResponse
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.directives.MethodDirectives
import dao.MovieDao
import model.Movie
import scala.concurrent.ExecutionContextExecutor

trait MovieRoutes extends SprayJsonSupport {
  this: MovieDao =>

  implicit val dispatcher: ExecutionContextExecutor

  val routes = pathPrefix("movies") {
    pathEnd {
      get {
        complete(getAll)
      } ~ post {
        entity(as[Movie]) { movie =>
          complete {
            create(movie).map { result => HttpResponse(entity = "movie has been saved successfully") }
          }
        }
      }
    } ~ 
      path(IntNumber) { id =>
        get {
          complete(getById(id))
        } ~ put {
          entity(as[Movie]) { movie =>
            complete {
              val newMovie = Movie(movie.title, Option(id))
              update(newMovie).map { result => HttpResponse(entity = "movie has been updated successfully") }
            }
          }
        } ~ MethodDirectives.delete {
          complete {
            delete(id).map { result => HttpResponse(entity = "movie has been deleted successfully") }
          }
        }
      }
  }
}
