class RomanNumeral(val value: Int) {

}

object RomanNumeral {
  //TODO: Verify D/L are correct.
  private val baseNumerals = Map(
    "I" -> 1,
    "V" -> 5,
    "X" -> 10,
    "L" -> 50,
    "C" -> 100,
    "D" -> 500,
    "M" -> 1000
  )

  def apply(numeral: String): RomanNumeral = {
    val value = numeral.map(
      c => baseNumerals.getOrElse(c.toString, 1)
    ).sum

    return new RomanNumeral(value)
  }

}
