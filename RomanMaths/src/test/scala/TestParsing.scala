import org.scalatest.{AppendedClues, FreeSpec, Matchers, TryValues}

import scala.util.Try

class TestParsing extends FreeSpec with NumeralParser with Matchers with AppendedClues with TryValues {

  val parser = new NumeralParser {}

  "A string parsed as a numeral" - {
    "when valid" - {
      "should parse to a roman numeral" in {
        val result = parser.parse(parser.romanNumeral, "XV")
        result shouldBe a [Success[_]]
        result.get shouldBe 15
      }
    }
    "when invalid" - {
      "should raise an error" in {
        val result = Try(parser.parse(parser.romanNumeral, "VX"))
        result.failure.exception shouldBe a [IllegalArgumentException]
      }
    }
  }

  "A string parsed with an operator" - {
    "for an addition operator" - {
      "should return the summed value" in {
        val result = parser.parse(parser.plus, "XV + IV")
        result shouldBe a [Success[_]]
        result.get shouldBe 19
      }
    }
    "for a subtraction operator" - {
      "should return the subtracted value" in {
        val result = parser.parse(parser.minus, "XV - IV")
        result shouldBe a[Success[_]]
        result.get shouldBe 11
      }
    }
    "for a multiplication operator" - {
      "should return the multiplied value" in {
        val result = parser.parse(parser.times, "XV * II")
        result shouldBe a [Success[_]]
        result.get shouldBe 30
      }
     }
    "for an division operator" - {
      "should return the integer divided value" in {
        val result = parser.parse(parser.divides, "XV / IV")
        result shouldBe a[Success[_]]
        result.get shouldBe 3
      }
    }
  }
}
