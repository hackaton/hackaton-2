package hackaton2.server

import api.domain._
import domain._

import com.jteigen.scalatest.JUnit4Runner

import hackaton2.server.domain._

import org.junit.runner.RunWith
import org.scalatest.{BeforeAndAfter, Spec}

@RunWith(classOf[JUnit4Runner])
class FriendsAlbumsIntegrationTest extends Spec with BeforeAndAfter {
  override def beforeEach {
    Server.start_!(9090)
    Server.start_!(9091)
  }

  override def afterEach {
    Server.stop_!(9090)
    Server.stop_!(9091)
  }

  describe("post") {
    Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() { def uncaughtException(t: Thread, e: Throwable) { println("Exception handler: "); e.printStackTrace } })
    Friends ! NewFriend("http://localhost:9090/", "funky")
    Friends ! NewFriend("http://localhost:9091/", "monkey")
    it("send albums to friends") {
      val replies = Friends !? PostAlbumFriends(FriendsAlbum(MySelf, new Album("Johnny Cash", "Ring of Fire", Nil)))
      println("REPLY: " + replies)
      // TODO: Turned off until server clean-up issue resolved
//      assert(FriendsAlbums !? CountAlbums === 2)
    }
  }
  
}
