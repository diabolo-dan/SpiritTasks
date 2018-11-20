import org.scalatest.{AppendedClues, FreeSpec, Matchers, TryValues}

import scala.util.Try

class TestConversion extends FreeSpec with Matchers with AppendedClues with TryValues {
  def assertNumeral(numeral: String, expectedValue: Int) =
    RomanNumeral(numeral).success.value.value shouldBe expectedValue withClue s": Numeral: $numeral"

  "A Roman Numeral" - {
    "When written individually" - {
      "should be converted to the correct number" in {
        assertNumeral("I", 1)
        assertNumeral("V", 5)
        assertNumeral("X", 10)
        assertNumeral("L", 50)
        assertNumeral("C", 100)
        assertNumeral("D", 500)
        assertNumeral("M", 1000)
      }
    }

    "When Combined" - {
      "should be converted into the correct number" in {
        assertNumeral("MCX", 1110)
        assertNumeral("XVI", 16)
      }
    }

    def assertBadNumeral(triedNumeral: Try[RomanNumeral]) = {
      triedNumeral.failure.exception shouldBe a [InvalidNumeral]
    }

    "When including invalid characters" - {
      "should raise an InvalidNumeral exception" in {
        assertBadNumeral(RomanNumeral("R"))
      }

    }
  }


}
