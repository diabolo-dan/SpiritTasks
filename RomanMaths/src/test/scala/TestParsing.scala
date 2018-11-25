import org.scalatest.{AppendedClues, FreeSpec, Matchers, TryValues}

import scala.util.Try

class TestParsing extends FreeSpec with NumeralParser with Matchers with AppendedClues with TryValues {

  val parser = new NumeralParser {}

  "A string parsed as a numeral" - {
    "when valid" - {
      "should parse to a roman numeral" in {
        val result = parser.parse(parser.romanNumeral, "XV")
        result shouldBe a [Success[RomanNumeral]]
        result.get.value shouldBe 15
      }
    }
    "when invalid" - {
      "should raise an error" in {
        val result = Try(parser.parse(parser.romanNumeral, "VX"))
        result.failure.exception shouldBe a [IllegalArgumentException]
      }
    }
  }

}
