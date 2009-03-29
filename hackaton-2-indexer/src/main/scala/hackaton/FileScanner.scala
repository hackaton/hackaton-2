package hackaton

import hackaton2.api.{ToMap, FromMap}
import java.io.File

class FileScanner(basedir: String) {
  private def filesIn(dir: String) = new File(dir).listFiles

  def allFiles(f: File, files: List[File]): List[File] = (files /: f.listFiles)(
    (a, b) => if (b.isDirectory) allFiles(b, a) else b :: a)

  def files =
    allFiles(new File(basedir), Nil)

  def songs =
    files.filter(f => f.getName.endsWith(".flac")).map(Song.apply)

  def albums = (Map[(String, String), Album]() /: songs) {
    (a, song) =>
            val key = (song.artist, song.album)
            val optAlbum = a.get(key).map(_ + song)
            a(key) = optAlbum.getOrElse(Album(song.artist, song.album, List(song)))
  }.values.toList
}

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

object Album extends FromMap[Album] {
  def apply(map: Map[String, Any]) = Album(map.string("artist"), map.string("name"), map("songs").asInstanceOf[List[Map[String, Any]]].map(Song.apply(_)))
}

case class Album(artist: String, name: String, songs: List[Song]) extends ToMap {
  def +(song: Song) =
    Album(artist, name, song :: songs)

  def toMap = Map("artist" -> artist, "name" -> name, "songs" -> songs.map(_.toMap))
}