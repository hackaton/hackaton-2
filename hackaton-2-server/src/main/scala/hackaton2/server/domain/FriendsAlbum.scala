package hackaton2.server.domain

import api.{FromMap, ToMap}

object FriendsAlbum extends FromMap[FriendsAlbum] {
  def apply(data:Map[String,Any]) = FriendsAlbum(Friend(data("friend").map), Album(data("album").map))  
}

case class FriendsAlbum(friend: Friend, album: Album) extends ToMap {
  def toMap = Map("friend" -> friend, "album" -> album)
}
