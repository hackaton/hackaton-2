package hackaton2.server.domain


import rest.{FromMap, ToMap}

object Friend extends FromMap[Friend] {
  def apply(map:Map[String,Any]) = Friend(map("id").asInstanceOf[String], map("url").asInstanceOf[String], map("nick").asInstanceOf[String])
}

case class Friend(id:String, url:String, nick:String) extends ToMap {
  def toMap = Map("id" -> id, "url" -> url, "nick" -> nick)
}