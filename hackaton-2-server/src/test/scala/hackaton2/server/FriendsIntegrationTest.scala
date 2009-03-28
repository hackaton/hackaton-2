package hackaton2.server


import api.FromJSON
import com.jteigen.scalatest.JUnit4Runner
import domain.{Friend, NewFriend}
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

    it("post /friends should return new friend id"){
      val newFriend = NewFriend("http://foo.com", "foo")
      Friend(FromJSON.map(post("friends", newFriend))) should be(Friend(1, newFriend.url, newFriend.nick))
    }
  }
}