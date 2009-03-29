package hackaton

import hackaton2.api.ToJSON
import hackaton2.api.domain._
import java.io.File
import java.net.URL
import org.junit.Test
import org.junit.Assert.assertTrue

class FileScannerTest {
  @Test
  def albums() {
    val url = getClass.getResource("/music")
    val music = new File(url.toURI)
    val fileScanner = new FileScanner(music.getPath)

    val albums = List[Album](
      Album("TheBand", "TheAlbum", List[Song](
        Song("TheBand", "TheAlbum", 1, "Song1"),
        Song("TheBand", "TheAlbum", 2, "Song2"),
        Song("TheBand", "TheAlbum", 3, "Song3")
        )),
      Album("OtherBand", "OtherAlbum", List[Song](
        Song("OtherBand", "OtherAlbum", 1, "Song1"),
        Song("OtherBand", "OtherAlbum", 2, "Song2")
        ))
      )

    assertTrue(albums == fileScanner.albums)
  }
}
