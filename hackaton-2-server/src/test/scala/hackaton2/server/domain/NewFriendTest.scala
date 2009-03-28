package hackaton2.server.domain


import com.jteigen.scalatest.JUnit4Runner
import org.junit.runner.RunWith
import org.scalatest.FunSuite

@RunWith(classOf[JUnit4Runner])
class NewFriendTest extends FunSuite {
  test("should serialize to itself"){
    val newFriend = NewFriend("http://foo.com", "mynick")
    assert(NewFriend(newFriend.toMap) === newFriend) 
  }
}