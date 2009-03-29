package hackaton2.server

import com.jteigen.scalatest.JUnit4Runner

import hackaton2.server.domain._

import org.junit.runner.RunWith
import org.scalatest.{BeforeAndAfter, Spec}

@RunWith(classOf[JUnit4Runner])
class FriendsAlbumsIntegrationTest extends Spec with BeforeAndAfter {
  val serverA = new Server
  val serverB = new Server
  
  override def beforeEach {
    serverA.start_!(9090)
    serverB.start_!(9091)
  }

  override def afterEach {
    serverA.stop_!
    serverB.stop_!
  }

  describe("post") {
    Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() { def uncaughtException(t: Thread, e: Throwable) { println("Exception handler: "); e.printStackTrace } })
    Friends ! NewFriend("http://localhost:9090/", "funky")
    Friends ! NewFriend("http://localhost:9091/", "monkey")
    it("send albums to friends") {
      val replies = Friends !? PostAlbumFriends(FriendsAlbum(new Friend(3, "http://snorkelhost:1984", "snorkel"), new Album(1, new Artist(1, "Johnny Cash"), "Ring of Fire")))
      println("REPLY: " + replies)
//      assert(FriendsAlbums !? CountAlbums === 2)
    }
  }
  
}
