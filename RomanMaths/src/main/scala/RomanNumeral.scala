import scala.util.Try

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

  def apply(numeral: String): Try[RomanNumeral] = Try{
    val value = numeral.map(
      c => baseNumerals.getOrElse(c.toString, {throw new InvalidNumeral()})
    ).sum
    new RomanNumeral(value)
  }

}

class InvalidNumeral extends IllegalArgumentException
