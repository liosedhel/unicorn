Migration to 1.0.x
=============================

Version 1.0.x brings Slick 3 new `DBIOAction` based API to Unicorn. We dropped `implicit session` parameter from method signatures but we had wrap return types in `DBIOAction`. We also added `implicit executionContext` in a few places.

E.g. old `BaseIdRepository` methods:
```
  def findById(id: Id)(implicit session: Session): Option[Entity]
  def findExistingById(id: Id)(implicit session: Session): Entity
```
now become
```
  def findById(id: Id): DBIO[Option[Entity]]
  def findExistingById(id: Id)(implicit ec: ExecutionContext): DBIO[Entity]
```
To get to know more about `DBIOAction`s check out [Slick 3 documentation](http://slick.typesafe.com/docs/).


Migration form 0.5.x to 0.6.x
=============================

Version 0.6.x brings possibility for using different type then `Long` as underlying `Id` type.
The most interesting are `UUID` and `String`. This change allow us to start working on typesafe composite keys.

For backward compatibility with `0.5.x` we introduced `LongUnicornCore` and `LongUnicornPlay`.
Before attempting to perform this migration you should known how to migrate your tables definitions to `slick-2.1.x`.
All needed information you could find in awesome [migration guide](http://slick.typesafe.com/doc/2.1.0/upgrade.html#upgrade-from-2-0-to-2-1)


Core migration
--------------

Changes is only on backing your version on unicorn cake. So code like:
```
object Unicorn extends UnicornCore with HasJdbcDriver {
  val driver = H2Driver
}
```
now becomes:
```
object Unicorn extends LongUnicornCore with HasJdbcDriver {
  val driver = H2Driver
}
```
and this is all your changes.

Play migration
--------------

When you use `unicorn-play` migration is still quite easy,
but it will touch all file where unicorn and slick was used.

Imports like those:
```
import org.virtuslab.unicorn.UnicornPlay._
import org.virtuslab.unicorn.UnicornPlay.driver.simple._
```
now becomes:
```
import org.virtuslab.unicorn.LongUnicornPlay._
import org.virtuslab.unicorn.LongUnicornPlay.driver.simple._
```
