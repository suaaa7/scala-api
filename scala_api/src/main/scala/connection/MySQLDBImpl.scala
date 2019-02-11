package connection

import slick.driver.MySQLDriver

trait MySQLDBImpl {

  val driver = MySQLDriver

  import driver.api._

  val db: Database = MySQLDB.connectionPool

}

private[connection] object MySQLDB {

  import slick.driver.MySQLDriver.api._

  val connectionPool = Database.forConfig("mysql")

}

