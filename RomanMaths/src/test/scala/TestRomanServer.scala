import io.undertow.Undertow
import org.scalatest.{FreeSpec, Matchers}

class TestRomanServer extends FreeSpec with Matchers {
  def test[T](example: cask.main.BaseMain)(f: String => T): T = {
    val server = Undertow.builder
      .addHttpListener(8080, "localhost")
      .setHandler(example.defaultHandler)
      .build
    server.start()
    val res =
      try f("http://localhost:8080")
      finally server.stop()
    res
  }

  "MinimalApplication" - {
    "should respond with failure to bad end points" in {
      test(RomanMathServer) { host =>
        requests.get(host).statusCode shouldBe 400
        requests.get(s"$host/doesnt-exist").statusCode shouldBe 404
        requests.get(s"$host/do-thing").statusCode shouldBe 404
      }
    }
    "should succesfully respond to correct requests in" in {
      test(RomanMathServer) { host =>
        val success = requests.get(host, params= Map("expr" -> "X + X * II"))
        success.statusCode shouldBe 200
        success.text() shouldBe "XXX"
      }
    }
    "should respond with failure to unparseable data" in {
      test(RomanMathServer) { host =>
        requests.get(host, params = Map("expr" -> "IC")).statusCode shouldBe 400
        requests.get(host, params = Map("expr" -> "C + * X")).statusCode shouldBe 400
      }
    }
  }
}

