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
    val numeralValue = decomposeNumeral(NumeralDecomposition(numeral, 0)).value
    val constructedNumeral = new RomanNumeral(numeralValue)
    require(constructedNumeral.display == numeral, s"Invalid Numeral: $numeral (${constructedNumeral.display})")
    constructedNumeral
  }

  private def decomposeNumeral(numeralDecomposition: NumeralDecomposition): NumeralDecomposition = {
    val value =
      orderedValues
        .map(_._1)
        .sortBy(- _.size)
        .foldLeft(numeralDecomposition)(extractValueForComponent)
    value
  }

  private def extractValueForComponent(state: NumeralDecomposition , component: String): NumeralDecomposition =
    state.removeComponent(component)

  private case class NumeralDecomposition(remainingNumeral: String, value: Int) {

    def removeComponent(component: String): NumeralDecomposition =
      NumeralDecomposition(
        reducedNumeral(component),
        value + componentValue(component)
      )

    private def reducedNumeral(component: String): String =
      remainingNumeral.replaceAll(component, "")

    private def componentValue(component: String): Int =
        numberOfMatches(component) * numeralValue(component)

    private def numberOfMatches(component: String): Int =
      component
        .r
        .findAllMatchIn(remainingNumeral)
        . size
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

  val numeralValue = orderedValues.toMap
}
