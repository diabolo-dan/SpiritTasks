import scala.util.Try
import scala.util.parsing.combinator.RegexParsers

trait NumeralParser extends RegexParsers {
  /*
  Parser based on the code found in the answer to:
   https://stackoverflow.com/questions/11533547/operator-precedence-with-scala-parser-combinators
  This was the most straightforward parser that I could find, and is at least as clear as I could come up with
  independently
   */

  def romanNumeral: Parser[BigInt] = """[A-Z]+""".r ^? {
    case x if Try(RomanNumeral(x)).isSuccess => RomanNumeral(x).value
  }

  def expr:   Parser[BigInt]        = term ~ rep(plus | minus) ^^ {case a ~ b => (a /: b)((acc,f) => f(acc))}
  def plus:   Parser[BigInt => BigInt] = "+" ~ term               ^^ {case _ ~ b => _ + b}
  def minus:  Parser[BigInt => BigInt] = "-" ~ term               ^^ {case _ ~ b => _ - b}

  def term:   Parser[BigInt]        = powers ~ rep(times | divide) ^^ {case a ~ b => (a /: b)((acc,f) => f(acc))}
  def times:  Parser[BigInt => BigInt] = "*" ~ powers                 ^^ {case _ ~ b => _ * b }
  def divide: Parser[BigInt => BigInt] = "/" ~ powers                 ^^ {case _ ~ b => _ / b}

  def powers: Parser[BigInt] = factor ~ rep(power) ^^ {case a ~ b => (a /: b)((acc,f) => f(acc))}
  def power: Parser[BigInt => BigInt] =  "^" ~ factor ^^ {case _ ~ b => _ pow b.toInt }

  def factor: Parser[BigInt] = romanNumeral | "(" ~> expr <~ ")"

}

object NumeralParser extends NumeralParser {
  def parseExpression(expression: String): ParseResult[RomanNumeral] =
    parseAll(expr, expression) map {
      case value: BigInt => new RomanNumeral(value)
    }
}
