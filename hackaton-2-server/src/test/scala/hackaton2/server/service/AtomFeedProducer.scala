package hackaton2.server.service

import java.lang.String
import java.net.URL
import java.util.Date
import org.apache.abdera.Abdera
import org.apache.abdera.model.{Entry, Document, Feed}
import org.apache.abdera.parser.Parser

class AtomFeedProducer {
  def createAlbums: Feed = {
    val abdera = new Abdera
    val feed = abdera.newFeed
    feed.setId("1")
    feed.setTitle("Title")
    feed.setSubtitle("Subtitle")
    feed.setUpdated(new Date)
    feed.addLink("http://foo.com")

    val e = feed.addEntry
    e.setId("2")
    e.setTitle("Entry title")

    feed
  }

  def createPimpedAlbums = {
    import Scabdera.Feed._
    import Scabdera.Feed.{Entry => SEntry}
    import Scabdera.Feed.Entry.{Id => EId, Title => ETitle}

    Scabdera.Feed(
      Id("2"), Title("Title"), Subtitle("Subtitle"), Updated(new Date), Link("http://foo.com"),
      SEntry(EId("2"), ETitle("Entry Title")), SEntry(EId("4"), ETitle("Foo")))
  }

  object Scabdera {
    private[this] val abdera = new Abdera
    object Feed {
      object Prop {
        def apply[A](into: Feed => A => Any) = (a: A) => (feed: Feed) => into(feed)(a)
      }

      val Id = Prop(_.setId _)
      val Title = Prop(x => x.setTitle(_: String))
      val Subtitle = Prop(x => x.setSubtitle(_: String))
      val Updated = Prop(x => x.setUpdated(_: Date))
      val Link = Prop(_.addLink _)

      def apply(props: (Feed => Any)*) = {
        val feed = abdera.newFeed
        props.foreach(_.apply(feed))
        feed
      }

      object Entry {
        object Prop {
          def apply[A](into: Entry => A => Any) = (a: A) => (entry: Entry) => into(entry)(a)
        }

        val Id = Prop(_.setId _)
        val Title = Prop(x => x.setTitle(_: String))

        def apply(props: (Entry => Any)*) = (feed: Feed) => {
          val entry = feed.addEntry
          props.foreach(_.apply(entry))
          entry
        }
      }
    }
  }
}

