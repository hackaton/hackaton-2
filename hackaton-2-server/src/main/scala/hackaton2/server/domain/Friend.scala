package hackaton2.server.domain

import api.{FromMap, ToMap}

object Friend extends FromMap[Friend] {
  def apply(map:Map[String,Any]):Friend = Friend(map("id").int, map("url").string, map("nick").string)
}

case class Friend(id:Int, url:String, nick:String) extends ToMap {
  def toMap = Map("id" -> id, "url" -> url, "nick" -> nick)
}

object MySelf extends Friend(0, "http://localhost:9998/", "me")
