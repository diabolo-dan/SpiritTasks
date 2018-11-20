import org.scalatest.FreeSpec

class TestConversion extends FreeSpec {
  def assertNumeral(numeral: String, expectedValue: Int) =
    assert(RomanNumeral(numeral).value == expectedValue, s": Numeral: $numeral")

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
      }
    }
  }


}
