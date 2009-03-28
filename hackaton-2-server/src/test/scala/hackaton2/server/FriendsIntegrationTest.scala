package hackaton2.server


import com.jteigen.scalatest.JUnit4Runner
import domain.NewFriend
import java.io.InputStream
import org.junit.runner.RunWith
import java.net.{HttpURLConnection, URL}
import util.parsing.json.JSON

@RunWith(classOf[JUnit4Runner])
class FriendsIntegrationTest extends IntegrationSuite {

  describe("Friends resource"){

    it("get /friends should return empty list"){
      JSON.parse(get("friends")) should be(Some(Nil))
    }

    ignore("post /friends should return new friend id"){
      JSON.parseFull(post("friends", NewFriend("http://foo.com", "foo"))) should be(Map("ok" -> true, "id" -> "1"))
    }
  }
}