
object RomanMathServer extends cask.MainRoutes {
  override def host: String = "0.0.0.0"
  override def port: Int = 4000

  @cask.get("/")
  def respond(expr: String, request: cask.Request): cask.Response = {
    NumeralParser.parseExpression(expr) match {
      case NumeralParser.Success(numeral, _) =>
        response(200, numeral.display)
      case NumeralParser.Failure(message, _) =>
        response(400, message)
      case NumeralParser.Error(message, _) =>
        response(400, message)
    }
  }

  def response(statusCode: Int, data: String) =
    cask.Response(data, statusCode=statusCode, headers=enableCorsHeaders)


  def enableCorsHeaders : Seq[(String, String)] = Seq(
    "Access-Control-Allow-Origin" -> "*"
    , "Access-Control-Allow-Methods" -> "OPTIONS, GET"   // OPTIONS for pre-flight
    , "Access-Control-Allow-Headers" -> "Accept, Content-Type, Origin, X-Json, X-Prototype-Version, X-Requested-With" //, "X-My-NonStd-Option"
    , "Access-Control-Allow-Credentials" -> "true"
  )
  initialize()
}
