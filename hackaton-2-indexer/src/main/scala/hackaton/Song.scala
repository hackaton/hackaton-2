package hackaton

import java.io.File
import hackaton2.api.{ToMap, FromMap}

object Song extends FromMap[Song] {
  private implicit def toInt(s: String) = Integer.parseInt(s.trim)

  def apply(map: Map[String, Any]) = Song(map.string("artist"), map.string("album"), map.int("trackNo"), map.string("track"))

  def apply(file: File): Song = {
    val parts = file.getName.substring(0, file.getName.lastIndexOf('.')).split("-").toList
    Song(parts.first.trim, parts(1).trim, parts(2), parts(3).trim)
  }
}

case class Song(artist: String, album: String, trackNo: Int, track: String) extends ToMap {
  def toMap = Map("artist" -> artist, "album" -> album, "trackNo" -> trackNo, "track" -> track)
}

