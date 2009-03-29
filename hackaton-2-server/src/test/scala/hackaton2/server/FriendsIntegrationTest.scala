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

    it("post /friends should return new friend id"){
      val id = (0 /: FromJSON.list(get("friends")).map(x => Friend(x.asInstanceOf[Map[String,Any]]).id))(_ max _) + 1
      val newFriend = NewFriend("http://foo.com", "foo")
      Friend(FromJSON.map(post("friends", newFriend))) should be(Friend(id, newFriend.url, newFriend.nick))
    }

    it("get /friends should return the newly added friend"){
      val friends = FromJSON.list(get("friends"))
      val newFriend = NewFriend("http://foo.com", "foo")
      post("friends", newFriend)
      val newPersistentFriend = (FromJSON.list(get("friends")) -- friends).map(x => Friend(x.asInstanceOf[Map[String,Any]]))
      newPersistentFriend.length should be(1)
      newPersistentFriend.head.url should be(newFriend.url)
      newPersistentFriend.head.nick should be(newFriend.nick)
    }
  }
}