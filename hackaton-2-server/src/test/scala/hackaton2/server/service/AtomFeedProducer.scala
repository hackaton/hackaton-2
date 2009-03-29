package hackaton2.server.service

import java.lang.String
import java.net.URL
import java.util.Date
import org.apache.abdera.Abdera
import org.apache.abdera.model.{Entry, Document, Feed}
import org.apache.abdera.parser.Parser

class AtomFeedProducer {

  def createAlbums: Feed = {
    var abdera = new Abdera
    var feed = abdera.newFeed
    feed.setId("1")
    feed.setTitle("Title")
    feed.setSubtitle("Subtitle")
    feed.setUpdated(new Date)
    feed.addLink("http://foo.com")

    var e = feed.addEntry
    e.setId("2")
    e.setTitle("Entry title")

    feed
  }
}
