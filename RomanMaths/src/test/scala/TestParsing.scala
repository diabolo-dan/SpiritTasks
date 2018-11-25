import org.scalatest.{AppendedClues, FreeSpec, Matchers, TryValues}

import scala.util.Try

class TestParsing extends FreeSpec with NumeralParser with Matchers with AppendedClues with TryValues {

  val parser = new NumeralParser {}

  "A string parsed as a numeral" - {
    "when valid" - {
      "should parse to a roman numeral" in {
        val result = parser.parse(parser.romanNumeral, "XV")
        result shouldBe a [Success[Int]]
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
        result shouldBe a [Success[RomanNumeral]]
        result.get shouldBe 19
      }
    }
  }

}
