package hackaton

import java.io.File
import org.codehaus.mojo.unix.util.SystemCommand
import hackaton2.api.domain.Album

class CTorrent(torrentCmd: File) {

  def createFor(album: Album) {
    new SystemCommand().
            addArgument(torrentCmd.getPath).
            addArgument("-t").
            addArgument("-s").
            addArgument("http://tracker.no")
//            addArgument(file.getPath)
  }
}