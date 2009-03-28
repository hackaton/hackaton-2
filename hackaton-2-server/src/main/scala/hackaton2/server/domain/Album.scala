package hackaton2.server.domain

import rest.{FromMap, ToMap}

object Album extends FromMap[Album] {
  def apply(map:Map[String,Any]) = Album(map.int("id"), Artist.apply(map.get("artist").asInstanceOf: Map[String,Any]), map.string("name"))
}

case class Album(id: Int, artist: Artist, name: String) extends ToMap {
  def toMap = Map("id" -> id, "artist" -> artist, "name" -> name)
}
