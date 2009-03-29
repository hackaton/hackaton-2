package hackaton2.server

import api.domain._
import domain._

import com.jteigen.scalatest.JUnit4Runner

import hackaton2.server.domain._
import java.io.File
import org.junit.runner.RunWith
import org.scalatest.{BeforeAndAfter, Spec}

@RunWith(classOf[JUnit4Runner])
class AlbumsSearchIntegrationTest extends Spec with BeforeAndAfter {
  
  override def beforeEach {
    Server.start_!(9090)
    Server.start_!(9091)
    Server.start_!(9998)
  }

  override def afterEach {
    Server.stop_!(9090)
    Server.stop_!(9091)
    Server.stop_!(9998)
  }

  def within(ms: Int, fun: => Option[String]): Option[String] = {
    if (ms < 0) 
      fun
    else {
      if (fun == None)
        None
      else {
        Thread.sleep(10)
        within(ms - 10, fun)
      }
    }
  }
  
  describe("post") {
    Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() { def uncaughtException(t: Thread, e: Throwable) { println("Exception handler: "); e.printStackTrace } })
    AlbumsSearchMatches !? ClearAlbumsSearchMatches
    Friends !? ClearFriends
    Friends !? NewFriend("http://localhost:9090/", "ha")
    Friends !? NewFriend("http://localhost:9091/", "ho")
    it("send albums search requests to friends and receive answers") {
      Albums !? Album("Pink Floyd", "More", Nil, new File(""))
      Albums !? Album("Pet Shop Boys", "Stupid", Nil, new File(""))
      assert(AlbumsSearchMatches.all.size === 0)
      println("POSTING SEARCH: " + (Friends !? AlbumsSearch(MySelf, "floyd")))
      assert(within(2000, AlbumsSearchMatches.all.size === 2))
      Albums !? Album("Floyd Daily", "Less", Nil, new File(""))
      assert(within(2000, AlbumsSearchMatches.all.size === 4))
    }
  }
  
}
