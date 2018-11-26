class RomanNumeral(val value: Int) {

  lazy val display: String = {
    for {
      (numeralValue, numeral) <- RomanNumeralMappings.orderedValues.dropWhile(_._1 > value).headOption
      numberOfAppearances = value / numeralValue
      remaining = value % numeralValue
    } yield numeral * numberOfAppearances +  new RomanNumeral(remaining).display
  }.getOrElse("")
}

object RomanNumeral {

  def apply(numeral: String): RomanNumeral = {
    val result = aggregateNumeral(numeral)
    require(result.display == numeral, s"Invalid Numeral: $numeral")
    result
  }

  private def aggregateNumeral(numeral: String): RomanNumeral = {
    val aggregatorZero = AggregationState(numeral, 0)
    val aggregation = RomanNumeralMappings.orderedNumerals.foldLeft(aggregatorZero)(aggregationOperator)
    new RomanNumeral(aggregation.value)
  }

  private def aggregationOperator(a: AggregationState, b: String): AggregationState =
    a.numeralValueAggregator(b)

  private case class AggregationState(numeral: String, value: Int) {

    def numeralValueAggregator(target: String): AggregationState = {
      val (matching, remaining) = numeral.span(_.toString == target)
      val newValue = matching.size * RomanNumeralMappings.baseNumerals(target)
      AggregationState(remaining, value + newValue).addPrefixValues(target) }

    private def addPrefixValues(target: String): AggregationState = {
      for {
        prefix <- RomanNumeralMappings.prefixNumerals(target)
        prefixedTarget = prefix + target
        if (numeral.startsWith(prefixedTarget))
      }
          yield AggregationState(numeral.substring(prefixedTarget.length), value + RomanNumeralMappings.compositeNumerals(prefixedTarget))
    }.getOrElse(this)
  }
}

object RomanNumeralMappings {

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

  val orderedNumerals =
    baseNumerals.toSeq.sortBy(_._2).map(_._1).reverse


  val orderedValues =
    for {
      (s, v) <- (baseNumerals ++ compositeNumerals).toSeq.sortBy(- _._2)
    } yield (v, s)
}
