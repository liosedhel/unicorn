package org.virtuslab.unicorn

import slick.lifted.MappedTo

trait IdentifiersWrapper[Underlying] {
  val identifiers: Identifiers[Underlying]
}

trait Identifiers[Underlying] {

  def ordering: Ordering[Underlying]

  /**
   * Base trait for implementing ids.
   * It is existential trait so it can have only defs.
   */
  trait BaseId extends Any with MappedTo[Underlying] {
    def id: Underlying
    override def value: Underlying = id
  }

  /**
   * Base class for companion objects for id classes.
   * Adding this will allow you not to import mapping from your table class every time you need it.
   *
   * @tparam Id type of Id
   */
  abstract class CoreCompanion[Id <: BaseId] {

    /** Ordering for ids */
    implicit val basicOrdering = Ordering.by[Id, Id#Underlying](_.value)(ordering)
  }

  /**
   * Base class for all entities that contains an id.
   * @tparam Id type of Id
   */
  trait WithId[Id <: BaseId] {

    /** @return id of entity (optional, entities does not have ids before save) */
    def id: Option[Id]
  }

  import scala.language.higherKinds

  type IdCompanion[Id <: BaseId]

}
