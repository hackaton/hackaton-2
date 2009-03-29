package hackaton2.server.domain

import api.{FromMap, ToMap}

object Artist extends FromMap[Artist] {
  def apply(map:Map[String,Any]) = Artist(map("id").int, map("name").string)
}

case class Artist(id: Int, name: String) {
}
