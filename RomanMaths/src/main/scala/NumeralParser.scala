import scala.util.parsing.combinator.RegexParsers

trait NumeralParser extends RegexParsers {
  def romanNumeral: Parser[Int] = """[A-Z]+""".r ^^ {RomanNumeral(_).get.value}

  def plus: Parser[Int] = romanNumeral ~ "+" ~ romanNumeral ^^ {case a~"+"~b => a + b}
}
