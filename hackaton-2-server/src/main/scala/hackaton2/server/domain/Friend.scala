package hackaton2.server.domain


import rest.{FromMap, ToMap}

object Friend extends FromMap[Friend] {
  def apply(map:Map[String,Any]) = Friend(map.int("id"), map.string("url"), map.string("nick"))
}

case class Friend(id:Int, url:String, nick:String) extends ToMap {
  def toMap = Map("id" -> id, "url" -> url, "nick" -> nick)
}