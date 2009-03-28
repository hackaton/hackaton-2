package hackaton2.server.domain


import rest.Mappable

case class Friend(id:String, url:String, nick:String) extends Mappable[Friend] {
  def toMap = Map("id" -> id, "url" -> url, "nick" -> nick)
  def fromMap(map:Map[String,Any]) = Friend(map("id").asInstanceOf[String], map("url").asInstanceOf[String], map("nick").asInstanceOf[String])
}