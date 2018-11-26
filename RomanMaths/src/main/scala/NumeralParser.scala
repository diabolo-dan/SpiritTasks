import scala.util.parsing.combinator.RegexParsers

trait NumeralParser extends RegexParsers {
  /*
  Parser based on the code found in the answer to:
   https://stackoverflow.com/questions/11533547/operator-precedence-with-scala-parser-combinators
  This was the most straightforward parser that I could find, and is at least as clear as I could come up with
  independently
   */

  def romanNumeral: Parser[Int] = """[A-Z]+""".r ^^ {RomanNumeral(_).value}

  def expr:   Parser[Int]        = term ~ rep(plus | minus) ^^ {case a ~ b => (a /: b)((acc,f) => f(acc))}
  def plus:   Parser[Int => Int] = "+" ~ term               ^^ {case _ ~ b => _ + b}
  def minus:  Parser[Int => Int] = "-" ~ term               ^^ {case _ ~ b => _ - b}

  def term:   Parser[Int]        = factor ~ rep(times | divide) ^^ {case a ~ b => (a /: b)((acc,f) => f(acc))}
  def times:  Parser[Int => Int] = "*" ~ factor                 ^^ {case _ ~ b => _ * b }
  def divide: Parser[Int => Int] = "/" ~ factor                 ^^ {case _ ~ b => _ / b}

  def factor: Parser[Int] = romanNumeral | "(" ~> expr <~ ")"

}

object NumeralParser extends NumeralParser {
  def parseExpression(expression: String): Option[RomanNumeral] =
    parseAll(expr, expression) match {
      case Success(value, _) => Option(new RomanNumeral(value))
      case _ => None
    }
}
