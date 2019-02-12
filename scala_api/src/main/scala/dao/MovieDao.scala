package dao

import connection.MySQLDBImpl
import model.{Movie, MovieTable}
import scala.concurrent.Future

trait MovieDao extends MovieTable with MySQLDBImpl {
  
  import driver.api._

  protected val movieTableQuery = TableQuery[MovieTable]

  protected def movieTableAutoInc = movieTableQuery returning movieTableQuery.map(_.id)

  def create(movie: Movie): Future[Int] = db.run {
    movieTableAutoInc += movie
  }

  def update(movie: Movie): Future[Int] = db.run {
    movieTableQuery.filter(_.id === movie.id.get).update(movie)
  }

  def getById(id: Int): Future[Option[Movie]] = db.run {
    movieTableQuery.filter(_.id === id).result.headOption
  }

  def getAll: Future[List[Movie]] = db.run {
    movieTableQuery.to[List].result
  }

  def delete(id: Int): Future[Int] = db.run {
    movieTableQuery.filter(_.id === id).delete
  }

  def ddl = db.run {
    movieTableQuery.schema.create
  }

}
