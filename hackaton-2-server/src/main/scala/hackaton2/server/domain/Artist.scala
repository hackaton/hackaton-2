package hackaton2.server.domain

import rest.{FromMap, ToMap}

object Artist extends FromMap[Artist] {
  def apply(map:Map[String,Any]) = Artist(map.int("id"), map.string("name"))
}

case class Artist(id: Int, name: String) {
}
