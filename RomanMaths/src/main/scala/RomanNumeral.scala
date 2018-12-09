class RomanNumeral(val value: BigInt) {

  def first_smaller_numeral: Option[(String, Int)] =
    RomanNumeral.orderedValues.dropWhile(_._2 > value).headOption

  lazy val display: String = {
    for {
      (numeral, numeralValue) <- first_smaller_numeral
      numberOfAppearances = value / numeralValue
      remaining = value % numeralValue
    } yield numeral * numberOfAppearances.toInt + new RomanNumeral(remaining).display
  }.getOrElse("")
}

object RomanNumeral {

  def apply(numeral: String): RomanNumeral = {
    val numeralValue = deconstructNumeral(numeral).value
    val constructedNumeral = new RomanNumeral(numeralValue)
    require(constructedNumeral.display == numeral, s"Invalid Numeral: $numeral (${constructedNumeral.display})")
    constructedNumeral
  }

  private def deconstructNumeral(numeral: String): Deconstruction = {
    val startState = this.Deconstruction(numeral, 0)
    baseNumeralsOrderedLongestFirst
      .foldLeft(startState)(extractValueForBaseNumeral)
  }

  private def extractValueForBaseNumeral(state: Deconstruction, baseNumeral: String): Deconstruction =
    state.removeBaseNumeral(baseNumeral)

  private case class Deconstruction(remainingNumeral: String, value: Int) {

    def removeBaseNumeral(baseNumeral: String): Deconstruction =
      Deconstruction(
        reducedNumeral(baseNumeral),
        value + baseNumeralValue(baseNumeral)
      )

    private def reducedNumeral(baseNumeral: String): String =
      remainingNumeral.replaceAll(baseNumeral, "")

    private def baseNumeralValue(baseNumeral: String): Int =
      numberOfMatches(baseNumeral) * numeralValue(baseNumeral)

    private def numberOfMatches(baseNumeral: String): Int =
      baseNumeral
        .r
        .findAllMatchIn(remainingNumeral)
        .size
  }

  val orderedValues = Seq(
    "M"  -> 1000,
    "CM" -> 900,
    "D"  -> 500,
    "CD" -> 400,
    "C"  -> 100,
    "XC" -> 90,
    "L"  -> 50,
    "XL" -> 40,
    "X"  -> 10,
    "IX" -> 9,
    "V"  -> 5,
    "IV" -> 4,
    "I"  -> 1
  )

  val baseNumeralsOrderedLongestFirst =
    orderedValues
      .map(_._1)
      .sortBy(-_.size)

  val numeralValue = orderedValues.toMap
}
