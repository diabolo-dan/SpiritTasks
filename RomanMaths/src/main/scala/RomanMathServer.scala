
object RomanMathServer extends cask.MainRoutes{

  @cask.get("/")
  def respond(expr: String): String = {
    val output = NumeralParser.parseExpression(expr)
    output.get.display
  }

  initialize()
}
