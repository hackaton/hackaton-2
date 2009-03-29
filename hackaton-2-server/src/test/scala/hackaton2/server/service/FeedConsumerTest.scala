package hackaton2.server.service


import com.jteigen.scalatest.JUnit4Runner
import org.junit.runner.RunWith
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.Spec

/**
 * @author <a href="mailto:kristoffer.moum@arktekk.no">Kristoffer Moum</a>
 */

@RunWith(classOf[JUnit4Runner])
class FeedConsumerTest extends Spec with ShouldMatchers {

  //Server.start_!(9091)

  describe("FeedConsumer") {
//    it("should receive feeds") {
//       (new FeedConsumer).getAlbums
//    }

    it("should get a goddamn album") {
      var albums = (new FeedConsumer).createAlbums
      println(albums)
    }

  }


}