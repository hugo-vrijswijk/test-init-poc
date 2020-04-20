package tst

import org.scalatest.funsuite.AnyFunSuite

class Test extends AnyFunSuite {
  test("Hello world") {
    assert(2 + 2 == 4)
  }
}
class Test2 extends munit.FunSuite {
  test("Hello world2") {
    assert(2 + 3 == 5)
  }
}
