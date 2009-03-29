package hackaton2.server.domain

import api.{FromMap, ToMap}
import api.domain._

object FriendsAlbum extends FromMap[FriendsAlbum] {
  def apply(map:Map[String,Any]) = FriendsAlbum(Friend.apply(getMap(map, "friend")), Album.apply(getMap(map, "album")))  
  def getMap(map: Map[String,Any], key: String) = map.get(key).asInstanceOf: Map[String,Any]
}

case class FriendsAlbum(friend: Friend, album: Album) extends ToMap {
  def toMap = Map("friend" -> friend, "album" -> album)
}
