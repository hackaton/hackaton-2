package hackaton2.server


import com.jteigen.scalatest.JUnit4Runner
import java.io.InputStream
import org.junit.runner.RunWith
import java.net.{HttpURLConnection, URL}

@RunWith(classOf[JUnit4Runner])
class FriendsIntegrationTest extends IntegrationSuite {
  describe("friends"){
    val http = new URL("http://localhost:9090/friends").openConnection.asInstanceOf[HttpURLConnection]

    it("should return some random stuff"){
      http.setRequestMethod("GET")
      http.connect
      val in: InputStream = http.getInputStream
      println(("" /: in)(_ + _.asInstanceOf[Char]))
      in.close
    }
  }
}