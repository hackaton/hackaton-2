package hackaton2.api.domain

import java.io.File
import hackaton2.api.{ToMap, FromMap}

object Album extends FromMap[Album] {
  def apply(map: Map[String, Any]) = Album(map.string("artist"), map.string("name"), map("songs").asInstanceOf[List[Map[String, Any]]].map(Song.apply(_)))
}

case class Album(artist: String, name: String, songs: List[Song]) extends ToMap {
  def +(song: Song) =
    Album(artist, name, song :: songs)

  def toMap = Map("artist" -> artist, "name" -> name, "songs" -> songs.map(_.toMap))
}