package hackaton2.server.domain

import api.{FromMap, ToMap}

object Album extends FromMap[Album] {
  def apply(map:Map[String,Any]):Album = Album(map("id").int, Artist(map("artist").asInstanceOf[Map[String,Any]]), map("name").string)
}

case class Album(id: Int, artist: Artist, name: String) extends ToMap {
  def toMap = Map("id" -> id, "artist" -> artist, "name" -> name)
}
