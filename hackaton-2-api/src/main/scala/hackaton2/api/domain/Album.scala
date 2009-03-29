package hackaton2.api.domain

import java.io.File
import hackaton2.api.{ToMap, FromMap}

object Album extends FromMap[Album] {
  def apply(map: Map[String, Any]) =
    Album(
      map("artist").string,
      map("name").string,
      map("songs").asInstanceOf[List[Map[String, Any]]].map(Song.apply(_)),
      new File(map("location").string))
}

case class Album(artist: String, name: String, songs: List[Song], location: File) extends ToMap {
  def +(song: Song) =
    Album(artist, name, song :: songs, location)

  def toMap = Map("artist" -> artist, "name" -> name, "songs" -> songs.map(_.toMap), "location" -> location.getPath)
  
  def matches(criteria: String) = {
    name.toLowerCase.contains(criteria) || 
      artist.toLowerCase.contains(criteria) || 
      songs.exists(_.track.toLowerCase.contains(criteria))
  }
}