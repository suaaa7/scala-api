import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.stream.ActorMaterializer
import dao.MovieDao
import http.MovieRoutes
import model.Movie
import scala.concurrent.ExecutionContextExecutor
import scala.io.StdIn

object Server extends App with MovieRoutes with MovieDao {

  implicit val system: ActorSystem = ActorSystem()

  implicit val materializer = ActorMaterializer()

  implicit val dispatcher: ExecutionContextExecutor = system.dispatcher

  ddl.onComplete {
    _ =>
      create(Movie("ドリクラ"))
      create(Movie("マリオ"))
      create(Movie("トロ"))

      val bindingFuture = Http().bindAndHandle(routes, "0.0.0.0", 8080)
      println(s"Server online at http://0.0.0.0:8080/\nPress RETURN to stop...")
      StdIn.readLine()
      bindingFuture
        .flatMap(_.unbind())
        .onComplete(_ => system.terminate())
  }
}
