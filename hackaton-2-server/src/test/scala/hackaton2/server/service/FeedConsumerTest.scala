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

  describe("AtomFeedProducer") {

    it("should get an  album") {
      var albums = (new AtomFeedProducer).createAlbums
      println(albums)
    }

  }

  describe("pimped abdera"){
    it("should be sweet"){
      println((new AtomFeedProducer).createPimpedAlbums)
    }
  }


}