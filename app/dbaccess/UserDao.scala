package dbaccess

import anorm.SQL
import play.api.Play.current
import play.api.db.DB
import anorm.NamedParameter.symbol
import models.User

/**
 * Data access object for user related operations.
 *
 * @author ob, scs
 */
trait UserDaoT {

  /**
   * Creates the given user in the database.
   * @param user the user object to be stored.
   * @return the persisted user object
   */
  def addUser(user: User): User = {
    DB.withConnection { implicit c =>
      val id: Option[Long] =
        SQL("insert into Users(name) values ({name})").on(
          'name -> user.name).executeInsert()
      user.id = id.get
    }
    user
  }

  /**
   * Removes a user by id from the database.
   * @param id the users id
   * @return a boolean success flag
   */
  def rmUser(id: Long): Boolean = {
    DB.withConnection { implicit c =>
      val rowsCount = SQL("delete from Users where id = ({id})").on('id -> id).executeUpdate()
      rowsCount > 0
    }
  }

  /**
   * Returns a list of available user from the database.
   * @return a list of user objects.
   */
  def registeredUsers: List[User] = {
    DB.withConnection { implicit c =>
      val selectUsers = SQL("Select id, name, admin_flag from Users;")
      // Transform the resulting Stream[Row] to a List[(String,String)]
      val users = selectUsers().map(row => User(row[Long]("id"), row[String]("name"), row[Boolean]("admin_flag"))).toList
      users
    }
  }

  /**
    * Returns a admin_flag of specific user from the database.
    * @return whether user is admin or not.
    */
  def getUser(name: String): Option[User] = {
    DB.withConnection { implicit c =>
      val selectUser = SQL("Select * from Users where name = {name} limit 1;").on('name -> name).apply
        .headOption
      selectUser match {
        case Some(row) => Some(User(row[Long]("id"), row[String]("name"), row[Boolean]("admin_flag")))
        case None => None
      }
      }
    }

}

object UserDao extends UserDaoT
