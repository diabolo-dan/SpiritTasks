import org.scalatest.{AppendedClues, FreeSpec, Matchers, TryValues}

import scala.util.Try

class TestParsing extends FreeSpec with NumeralParser with Matchers with AppendedClues with TryValues {

  val parser = new NumeralParser {}
  def parseExpression(expression: String): BigInt = {
    val result = parser.parseAll(parser.expr, expression)
    result shouldBe a[Success[_]]
    result.get
  }

  "A string parsed as a numeral" - {
    "when valid" - {
      "should parse to a roman numeral" in {
        parseExpression("XV") shouldBe 15
      }
    }
    "when invalid" - {
      "should raise an error" in {
        val result = Try(parseExpression("VX"))
        result.failure.exception shouldBe a [IllegalArgumentException]
      }
    }
  }

  "A string parsed with an operator" - {
    "for an addition operator" - {
      "should return the summed value" in {
        parseExpression("XV + IV") shouldBe 19
      }
    }
    "for a subtraction operator" - {
      "should return the subtracted value" in {
        parseExpression("XV - IV") shouldBe 11
      }
    }
    "for a multiplication operator" - {
      "should return the multiplied value" in {
        parseExpression("XV * II") shouldBe 30
      }
     }
    "for an division operator" - {
      "should return the integer divided value" in {
        parseExpression("XV / IV") shouldBe 3
      }
    }
    "for a composite statement" - {
      "with invalid combinations" - {
        "should fail" in {
          Try(parseExpression("+*")).isFailure shouldBe true
          Try(parseExpression("X + * X")).isFailure shouldBe true
          Try(parseExpression("X X")).isFailure shouldBe true
        }
      }
      "with additions" - {
        "should parse correctly" in {
          parseExpression("XV + IV + XX") shouldBe 39
        }
      }
      "with subtractions" - {
        "should parse correctly" in {
         parseExpression("XX - V - II")  shouldBe 13
        }
      }
      "with multiplications" - {
        "should parse correctly" in {
          parseExpression("V * II * IV") shouldBe 40
        }
      }
      "with divisions" - {
        "should parse correctly" in {
          parseExpression("XX / II / III") shouldBe 3
        }
      }
      "with powers" - {
        "should parse correctly" in {
          parseExpression("II ^ III ^ IV") shouldBe BigInt(2).pow(12)
        }
      }
      "with multiple forms" - {
        "should have the correct precedence" in {
          parseExpression("XX + X * II") shouldBe 40
          parseExpression("XX - X / II") shouldBe 15
          parseExpression("XX - X + II") shouldBe 12
          parseExpression("X * X / II") shouldBe 50
          parseExpression("III * X ^ II") shouldBe 300
        }
      }
      "with brackets" - {
        "should parse correctly" in {
          parseExpression("(XX + V) + V") shouldBe 30
          parseExpression("XX + (V + V)") shouldBe 30
          parseExpression("XX + (V) + (V)") shouldBe 30
          parseExpression("(XX + ((V) + (V)))") shouldBe 30
        }
        "should have maximum precedence for brackets" in {
          parseExpression("(X + X) * II") shouldBe 40
          parseExpression("X + (X * II)") shouldBe 30
          parseExpression("(X + X) * (II - II)") shouldBe 0
          parseExpression("X - (V + II)") shouldBe 3
        }
        "should error for improper brackets" in  {
          Try(parseExpression("(")).isFailure shouldBe true
          Try(parseExpression("(X +)")).isFailure shouldBe true
          Try(parseExpression("()X + X")).isFailure shouldBe true
          Try(parseExpression("() X")).isFailure shouldBe true
        }
      }
    }
  }
}
