package hackaton2.server.domain

import api.{FromMap, ToMap}

object Artist extends FromMap[Artist] {
  def apply(map:Map[String,Any]) = Artist(map.int("id"), map.string("name"))
}

case class Artist(id: Int, name: String) extends ToMap {
  def toMap = Map("id" -> id, "name" -> name)
}
