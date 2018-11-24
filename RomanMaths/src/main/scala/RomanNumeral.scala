import scala.util.Try

class RomanNumeral(val value: Int) {

  private def orderedValues =
    for {
      (s, v) <- (RomanNumeral.baseNumerals ++ RomanNumeral.compositeNumerals).toSeq.sortBy(- _._2)
    } yield (v, s)

  def display: String = {
    for {
      x <- orderedValues.dropWhile(_._1 > value).headOption
    } yield x._2 +  new RomanNumeral(value - x._1).display
  }.getOrElse("")
}

object RomanNumeral {

  val baseNumerals = Map(
    "I" -> 1,
    "V" -> 5,
    "X" -> 10,
    "L" -> 50,
    "C" -> 100,
    "D" -> 500,
    "M" -> 1000
  )

  val prefixNumerals = Map(
    "V" -> Option("I"),
    "X" -> Option("I"),
    "L" -> Option("X"),
    "C" -> Option("X"),
    "D" -> Option("C"),
    "M" -> Option("C")
  ).withDefaultValue(None)

  val compositeNumerals = {
    for {
      (numeral, value) <- baseNumerals
      prefix <- prefixNumerals(numeral)
    } yield (prefix + numeral -> (value - baseNumerals(prefix)))
  }

  private def orderedNumerals =
    baseNumerals.toSeq.sortBy(_._2).map(_._1).reverse

  def apply(numeral: String): Try[RomanNumeral] = Try{
    val aggregatorZero = AggregationState(numeral, 0)
    val result: AggregationState = orderedNumerals.foldLeft(aggregatorZero)(AggregationState.aggregate)
    if (!result.numeral.isEmpty) {
      throw new IllegalArgumentException(s"Invalid Numeral: $numeral")
    }
    new RomanNumeral(result.value)
  }


  object AggregationState {
    def aggregate(a: AggregationState, b: String): AggregationState =
      a.numeralValueAggregator(b)
  }


  case class AggregationState(numeral: String, value: Int) {

    private def numeralValueAggregator(target: String): AggregationState = {
      val (matching, remaining) = numeral.span(_.toString == target)
      val newValue = matching.size * baseNumerals(target)
      AggregationState(remaining, value + newValue).addPrefixValues(target) }

    private def addPrefixValues(target: String): AggregationState = {
      for {
        prefix <- prefixNumerals(target)
        prefixedTarget = prefix + target
        if (numeral.startsWith(prefixedTarget))
      }
          yield AggregationState(numeral.substring(prefixedTarget.length), value + compositeNumerals(prefixedTarget))
    }.getOrElse(this)
  }
}
