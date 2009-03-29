package hackaton2.server

import com.jteigen.scalatest.JUnit4Runner

import hackaton2.server.domain._

import org.junit.runner.RunWith
import org.scalatest.{BeforeAndAfter, Spec}

@RunWith(classOf[JUnit4Runner])
class AlbumsSearchIntegrationTest extends Spec with BeforeAndAfter {
  val serverA = new Server
  val serverB = new Server
  val me = new Server
  
  override def beforeEach {
    serverA.start_!(9090)
    serverB.start_!(9091)
    me.start_!(9092)
  }

  override def afterEach {
    serverA.stop_!
    serverB.stop_!
    me.stop_!
  }

  describe("post") {
    Thread.setDefaultUncaughtExceptionHandler(new Thread.UncaughtExceptionHandler() { def uncaughtException(t: Thread, e: Throwable) { println("Exception handler: "); e.printStackTrace } })
    Friends ! NewFriend("http://localhost:9090/", "ha")
    Friends ! NewFriend("http://localhost:9091/", "ho")
    it("send albums search requests to friends and receive answers") {
      Friends ! PostAlbumsSearch(AlbumsSearch(MySelf, "any"))
      // ...
    }
  }
  
}
