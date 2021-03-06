import org.scalatest.{AppendedClues, FreeSpec, Matchers, TryValues}

import scala.util.Try

class TestConversion extends FreeSpec with Matchers with AppendedClues with TryValues {

  def assertNumeral(numeral: String, expectedValue: Int) =
    RomanNumeral(numeral).value shouldBe expectedValue withClue s": Numeral: $numeral"

  def assertBadNumeral(numeral: String) =
    Try(RomanNumeral(numeral)).failure.exception shouldBe a [IllegalArgumentException]

  "A Roman Numeral" - {
    "When Constructed" - {
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

        "In the wrong order" - {
          "should raise an IllegalArgument Exception" in {
            assertBadNumeral("IC")
            assertBadNumeral("VX")
          }
        }

        "In allowed reverse Orderings" - {
          "should create the appropriate Value when alone" in {
            assertNumeral("IV", 4)
            assertNumeral("IX", 9)
            assertNumeral("XL", 40)
            assertNumeral("XC", 90)
            assertNumeral("CD", 400)
            assertNumeral("CM", 900)
          }

          "should create the appropriate value when composite" in {
            assertNumeral("MCMXIX", 1919)
          }
        }

        "With reverse orderings in the wrong order" - {
          "should raise an IllegalArgument exception" in {
            assertBadNumeral("IXX")
            assertBadNumeral("VIVV")
            assertBadNumeral("VIV")
          }
        }
      }

      "When including invalid characters" - {
        "should raise an IllegalArgument exception" in {
          assertBadNumeral("R")
          assertBadNumeral("XIA")
        }
      }
    }

    "When displayed" - {
      "should convert correctly" in {
        new RomanNumeral(12).display shouldBe "XII"
        new RomanNumeral(19).display shouldBe "XIX"
        new RomanNumeral(1919).display shouldBe "MCMXIX"
        new RomanNumeral(4).display shouldBe "IV"

      }
    }
  }
}
