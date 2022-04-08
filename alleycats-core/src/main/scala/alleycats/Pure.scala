/*
 * Copyright (c) 2022 Typelevel
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of
 * this software and associated documentation files (the "Software"), to deal in
 * the Software without restriction, including without limitation the rights to
 * use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of
 * the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS
 * FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR
 * COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER
 * IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN
 * CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package alleycats

import cats.{Applicative, FlatMap, Monad}
import simulacrum.typeclass

@typeclass trait Pure[F[_]] extends Serializable {
  def pure[A](a: A): F[A]
}

object Pure {
  // Ideally this would be an exported subclass instance provided by Applicative
  implicit def applicativeIsPure[F[_]](implicit ev: Applicative[F]): Pure[F] =
    new Pure[F] {
      def pure[A](a: A): F[A] = ev.pure(a)
    }

  // Ideally this would be an instance exported to Monad
  implicit def pureFlatMapIsMonad[F[_]](implicit p: Pure[F], fm: FlatMap[F]): Monad[F] =
    new Monad[F] {
      def pure[A](a: A): F[A] = p.pure(a)
      override def map[A, B](fa: F[A])(f: A => B): F[B] = fm.map(fa)(f)
      def flatMap[A, B](fa: F[A])(f: A => F[B]): F[B] = fm.flatMap(fa)(f)
      def tailRecM[A, B](a: A)(f: (A) => F[Either[A, B]]): F[B] = fm.tailRecM(a)(f)
    }

  /* ======================================================================== */
  /* THE FOLLOWING CODE IS MANAGED BY SIMULACRUM; PLEASE DO NOT EDIT!!!!      */
  /* ======================================================================== */

  /**
   * Summon an instance of [[Pure]] for `F`.
   */
  @inline def apply[F[_]](implicit instance: Pure[F]): Pure[F] = instance

  @deprecated("Use cats.syntax object imports", "2.2.0")
  object ops {
    implicit def toAllPureOps[F[_], A](target: F[A])(implicit tc: Pure[F]): AllOps[F, A] {
      type TypeClassType = Pure[F]
    } =
      new AllOps[F, A] {
        type TypeClassType = Pure[F]
        val self: F[A] = target
        val typeClassInstance: TypeClassType = tc
      }
  }
  trait Ops[F[_], A] extends Serializable {
    type TypeClassType <: Pure[F]
    def self: F[A]
    val typeClassInstance: TypeClassType
  }
  trait AllOps[F[_], A] extends Ops[F, A]
  trait ToPureOps extends Serializable {
    implicit def toPureOps[F[_], A](target: F[A])(implicit tc: Pure[F]): Ops[F, A] {
      type TypeClassType = Pure[F]
    } =
      new Ops[F, A] {
        type TypeClassType = Pure[F]
        val self: F[A] = target
        val typeClassInstance: TypeClassType = tc
      }
  }
  @deprecated("Use cats.syntax object imports", "2.2.0")
  object nonInheritedOps extends ToPureOps

  /* ======================================================================== */
  /* END OF SIMULACRUM-MANAGED CODE                                           */
  /* ======================================================================== */

}
