import hackaton2.api.ToJSON
import java.io.File
import java.net.URL
import org.junit.Test

class IndexerTest {

  @Test
  def files() {
    val url: URL = getClass.getResource("/music")
    val music: File = new File(url.toURI)
    val indexer: Indexer = new Indexer(music.getPath)

    for (song <- indexer.flacFiles)
      println(song)

    for (song <- indexer.flacFiles)
      println(ToJSON(song))
  }
}