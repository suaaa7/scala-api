package model

import connection.MySQLDBImpl
import spray.json.DefaultJsonProtocol

trait MovieTable extends DefaultJsonProtocol {
  this: MySQLDBImpl =>

  import driver.api._

  implicit lazy val movieFormat = jsonFormat2(Movie)
  implicit lazy val movieListFormat = jsonFormat1(MovieList)

  class MovieTable(tag: Tag) extends Table[Movie](tag, "movie") {
    val id = column[Int]("id", O.PrimaryKey, O.AutoInc)
    val title = column[String]("title")

    def * = (title, id.?) <>(Movie.tupled, Movie.unapply)

  }

}

case class Movie(title: String, id: Option[Int] = None)
case class MovieList(moives: List[Movie])

