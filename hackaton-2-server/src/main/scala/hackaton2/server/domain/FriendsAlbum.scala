package hackaton2.server.domain

import api.{FromMap, ToMap}
import api.domain._

object FriendsAlbum extends FromMap[FriendsAlbum] {
  def apply(map:Map[String,Any]) = FriendsAlbum(Friend.apply(map("friend").map), Album.apply(map("album").map))  
}

case class FriendsAlbum(friend: Friend, album: Album) extends ToMap {
  def toMap = Map("friend" -> friend, "album" -> album)
}
