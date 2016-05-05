package org.virtuslab.unicorn

import com.google.inject.Inject
import slick.backend.DatabaseConfig
import slick.driver.JdbcProfile
import slick.lifted.{ ProvenShape, Tag => SlickTag }
import play.api.data.format.Formats._

class StringUnicornPlay @Inject() (dbConfig: DatabaseConfig[JdbcProfile]) extends UnicornPlay[String](dbConfig) {
  override val identifiers: PlayIdentifiersImpl[String] = StringUnicornPlayIdentifiers
}

object StringUnicornPlayIdentifiers extends PlayIdentifiersImpl[String] {
  override val ordering: Ordering[String] = implicitly[Ordering[String]]
  override type IdCompanion[Id <: BaseId] = PlayCompanion[Id]
}

trait UserQuery {
  val unicornPlayLike: StringUnicornPlay
  import unicornPlayLike._
  import unicornPlayLike.identifiers._
  import unicornPlayLike.driver.api._

  case class UserId(id: String) extends BaseId

  object UserId extends IdCompanion[UserId]

  case class UserRow(id: Option[UserId], name: String) extends WithId[UserId]

  class UserTable(tag: SlickTag) extends IdTable[UserId, UserRow](tag, "test") {
    def name = column[String]("name")
    override def * : ProvenShape[UserRow] = (id.?, name) <> (UserRow.tupled, UserRow.unapply)
  }
}
