package hackaton

import hackaton2.api.ToJSON
import hackaton2.http.Http
import hackaton2.http.Http._
import java.net.URL
import java.io.File

/**
 * args(0) - basedir for music
 * args(1) - server url
 * args(2) - ctorrent binary
 * args(3) - tracker url
 */
object Indexer {
  def main(args: Array[String]) {
    val music = new File(args(0))
    val fileScanner = new FileScanner(music.getPath)
    val service = Http(args(1))
    val albums = fileScanner.albums
    val cTorrent = new CTorrent(new File(args(2)), args(3))
    albums.foreach(cTorrent.createFor)
    println(service.post[String, String](ToJSON(albums)))
  }
}
