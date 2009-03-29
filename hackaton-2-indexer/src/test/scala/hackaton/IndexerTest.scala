import hackaton2.api.ToJSON
import java.io.File
import java.net.URL
import org.junit.Test
import org.junit.Assert.assertTrue

class IndexerTest {
  @Test
  def albums() {
    val url: URL = getClass.getResource("/music")
    val music: File = new File(url.toURI)
    val indexer: Indexer = new Indexer(music.getPath)

    val albums = List[Album](
      Album("TheBand", "TheAlbum", List[Song](
        Song("TheBand", "TheAlbum", 1, "Song1"),
        Song("TheBand", "TheAlbum", 2, "Song2"),
        Song("TheBand", "TheAlbum", 3, "Song3")
        )),
      Album("OtherBand", "OtherAlbum", List[Song](
        Song("OtherBand", "OtherAlbum", 1, "Song1"),
        Song("OtherBand", "OtherAlbum", 2, "Song2")
        )),
      )

    assertTrue(albums == indexer.albums)
  }
}
