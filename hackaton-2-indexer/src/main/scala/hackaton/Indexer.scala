import collection.mutable.ListBuffer
import hackaton2.api.{ToMap, FromMap}
import java.io.File

class Indexer(basedir: String) {
  private def filesIn(dir: String) = new File(dir).listFiles

  def allFiles(f: File, files: List[File]): List[File] = (files /: f.listFiles)(
    (a, b) => if (b.isDirectory) allFiles(b, a) else b :: a)

  def files =
    allFiles(new File(basedir), Nil)

  def flacFiles =
    files.filter(f => f.getName.endsWith(".flac")).map(Song.apply)
}

object Song extends FromMap[Song] {
  private implicit def toInt(s: String) = Integer.parseInt(s.trim)

  def apply(map: Map[String, Any]) = Song(Artist(map.string("artist")), Album(map.string("album")), map.int("trackNo"), Track(map.string("track")))
  def apply(file: File): Song = {
    println(file)
    val parts = file.getName.substring(0, file.getName.lastIndexOf('.')).split("-").toList
    Song(Artist(parts.first.trim), Album(parts(1).trim), parts(2), Track(parts(3).trim))
  }
}

case class Song(artist: Artist, album: Album, trackNo: int, track: Track) extends ToMap {
  def toMap = Map("artist" -> artist.name, "album" -> album.name, "trackNo" -> trackNo, "track" -> track.name)
}

case class Artist(name: String)
case class Album(name: String)
case class Track(name: String)
