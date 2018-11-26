
object RomanMathServer extends cask.MainRoutes {
  override def host: String = "0.0.0.0"
  override def port: Int = 4000

  @cask.get("/")
  def respond(expr: String): String = {
    val output = NumeralParser.parseExpression(expr)
    output.get.display
  }

  initialize()
}
