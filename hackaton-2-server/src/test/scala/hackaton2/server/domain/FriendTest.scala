package hackaton2.server.domain


import com.jteigen.scalatest.JUnit4Runner
import org.junit.runner.RunWith
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.Spec

@RunWith(classOf[JUnit4Runner])
class FriendTest extends Spec with ShouldMatchers {
  describe("Friend"){
    it("should serialize to map"){
      Friend("1", "http://foo.com", "test").toMap should be(
        Map("id" -> "1", "url" -> "http://foo.com", "nick" -> "test"))
    }
  }
}