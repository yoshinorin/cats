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

package algebra
package ring

import scala.{specialized => sp}

/**
 * Rig consists of:
 *
 *  - a commutative monoid for addition (+)
 *  - a monoid for multiplication (*)
 *
 * Alternately, a Rig can be thought of as a ring without
 * multiplicative or additive inverses (or as a semiring with a
 * multiplicative identity).
 *
 * Mnemonic: "Rig is a Ring without 'N'egation."
 */
trait Rig[@sp(Int, Long, Float, Double) A] extends Any with Semiring[A] with MultiplicativeMonoid[A]

object Rig extends AdditiveMonoidFunctions[Rig] with MultiplicativeMonoidFunctions[Rig] {
  @inline final def apply[A](implicit ev: Rig[A]): Rig[A] = ev
}
