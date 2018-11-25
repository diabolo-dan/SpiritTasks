import org.scalatest.{AppendedClues, FreeSpec, Matchers, TryValues}

import scala.util.Try

class TestParsing extends FreeSpec with NumeralParser with Matchers with AppendedClues with TryValues {

  val parser = new NumeralParser {}
  def parseExpression(expression: String): Int = {
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
    }
  }
}
