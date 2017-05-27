package com.eclipsesource.schema.internal.refs

import com.osinka.i18n.Lang
import play.api.data.validation.ValidationError


/**
  * A typeclass that determines whether a value of the given
  * type can contain references
  *
  * @tparam A the type of document that can contain references
  */
trait CanHaveRef[A] {

  /**
    * Resolve the fragment against the given document. A fragment
    * is a single identifier, e.g. a property name.
    *
    * @param a the document value
    * @param fragment the fragment to be resolved
    * @return a right-based Either containing the result
    */
  def resolve(a: A, fragment: String)(implicit lang: Lang): Either[ValidationError, A]

  /**
    * Whether the given document value has an id field which can alter resolution scope.
    *
    * @param a the document instance to be checked
    * @return true, if the given instance has an id field, false otherwise
    */
  def refinesScope(a: A): Boolean = findScopeRefinement(a).isDefined

  /**
    * Tries to find an id field which refines the resolution scope.
    *
    * @param a the document instance to be checked
    * @return true, if the given instance has an id field, false otherwise
    */
  def findScopeRefinement(a: A): Option[Ref]

  /**
    * Returns any anchors.
    *
    * @param a a given document instance
    * @return a Map mapping any found anchors to the respective sub-documents
    */
  def anchorsOf(a: A): Map[Ref, A]

  /**
    * Tries to find a resolvable instance within the given value.
    *
    * @param a the given document value
    * @return an Option containing the field name and value, if any ref has been found
    */
  def findRef(a: A): Option[Ref]
}
