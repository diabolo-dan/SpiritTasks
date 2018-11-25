import scala.util.parsing.combinator.RegexParsers

trait NumeralParser extends RegexParsers {
  def romanNumeral: Parser[RomanNumeral] = """[A-Z]+""".r ^^ {RomanNumeral(_).get}

}
