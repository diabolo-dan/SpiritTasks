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

  private def orderedNumerals =
    baseNumerals.toSeq.sortBy(_._2).map(_._1).reverse

  def apply(numeral: String): Try[RomanNumeral] = Try{
    val aggregatorZero = (numeral, 0)
    val (remaining, value) = orderedNumerals.foldLeft(aggregatorZero)(numeralValueAggregator)
    if (!remaining.isEmpty) {
      throw new IllegalArgumentException(s"Invalid Numeral: $numeral")
    }
    new RomanNumeral(value)
  }

  private def numeralValueAggregator(previous: (String, Int), target: String): (String, Int) = {
    val (numeral, previousValue) = previous
    val (matching, remaining) = numeral.span(_.toString == target)
    val newValue = matching.size * baseNumerals(target)
    (remaining, previousValue + newValue)
  }


}
