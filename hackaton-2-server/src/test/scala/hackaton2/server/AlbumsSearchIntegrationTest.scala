package hackaton2.server

import com.jteigen.scalatest.JUnit4Runner

import hackaton2.server.domain._

import org.junit.runner.RunWith
import org.scalatest.{BeforeAndAfter, Spec}

@RunWith(classOf[JUnit4Runner])
class AlbumsSearchIntegrationTest extends Spec with BeforeAndAfter {
  
  override def beforeEach {
    Server.start_!(9090)
    Server.start_!(9091)
    Server.start_!(9092)
  }

  override def afterEach {
    Server.stop_!(9090)
    Server.stop_!(9091)
    Server.stop_!(9092)
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
