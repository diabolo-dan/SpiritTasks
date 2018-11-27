
object RomanMathServer extends cask.MainRoutes {
  override def host: String = "0.0.0.0"
  override def port: Int = 4000

  @cask.get("/")
  def respond(expr: String, request: cask.Request): cask.Response = {
    val output = NumeralParser.parseExpression(expr)

    cask.Response(output.get.display, headers=enableCorsHeaders)

  }

  def enableCorsHeaders : Seq[(String, String)] = Seq(
    "Access-Control-Allow-Origin" -> "*"
    , "Access-Control-Allow-Methods" -> "OPTIONS, GET, POST, PUT, DELETE, HEAD"   // OPTIONS for pre-flight
    , "Access-Control-Allow-Headers" -> "Accept, Content-Type, Origin, X-Json, X-Prototype-Version, X-Requested-With" //, "X-My-NonStd-Option"
    , "Access-Control-Allow-Credentials" -> "true"
  )
  initialize()
}
