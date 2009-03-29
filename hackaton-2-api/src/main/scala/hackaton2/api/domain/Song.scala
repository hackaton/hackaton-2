package hackaton2.api.domain

import java.io.File
import hackaton2.api.{ToMap, FromMap}

object Song extends FromMap[Song] {
  private implicit def toInt(s: String) = Integer.parseInt(s.trim)

  def apply(map: Map[String, Any]) =
    Song(
      map("artist").string,
      map("album").string,
      map("trackNo").int,
      map("track").string,
      new File(map("location").string)
      )

  def apply(file: File): Song = {
    val parts = file.getName.substring(0, file.getName.lastIndexOf('.')).split("-").toList
    Song(parts.first.trim, parts(1).trim, parts(2), parts(3).trim, file)
  }
}

case class Song(artist: String, album: String, trackNo: Int, track: String, location: File) extends ToMap {
  def toMap = Map(
    "artist" -> artist,
    "album" -> album,
    "trackNo" -> trackNo,
    "track" -> track,
    "location" -> location.getPath
    )
}

