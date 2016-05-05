package org.virtuslab.unicorn

import slick.driver.H2Driver

object LongUnicornIdentifiers extends Identifiers[Long] {
  override def ordering: Ordering[Long] = implicitly[Ordering[Long]]

  override type IdCompanion[Id <: BaseId] = CoreCompanion[Id]
}

object TestUnicorn
    extends LongUnicornCore
    with HasJdbcDriver {

  override val identifiers = LongUnicornIdentifiers
  override lazy val driver = H2Driver

}
