package hackaton

import java.io.File
import org.codehaus.mojo.unix.util.SystemCommand
import hackaton2.api.domain.Album

class CTorrent(torrentCmd: File, trackerUrl: String) {
  private def torrentNameFor(album: Album) =
    album.location.getPath + "/" + album.artist + "-" + album.name + ".torrent"

  def createFor(album: Album) {
    new SystemCommand().
            setCommand(torrentCmd.getPath).
            addArgument("-t").
            addArgument("-s").
            addArgument(torrentNameFor(album)).
            addArgument("-u").
            addArgument(trackerUrl).
            addArgument(album.location.getPath).
            execute().
            assertSuccess()
  }
}