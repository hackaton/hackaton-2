package hackaton2.server.domain

import api.{FromMap, ToMap}

object AlbumsSearch extends FromMap[AlbumsSearch] {
  def apply(map: Map[String, Any]) = AlbumsSearch(Friend(map("origin").map), map("criteria").string)
}

case class AlbumsSearch(origin: Friend, criteria: String) extends ToMap {
  def toMap() = Map("origin" -> origin, "criteria" -> criteria)
}
