package hackaton2.server.domain

import api.{FromMap, ToMap}

object AlbumsSearchMatch extends FromMap[AlbumsSearchMatch] {
  def apply(map: Map[String, Any]) = AlbumsSearchMatch(AlbumsSearch.apply(map("albumsSearch").map), FriendsAlbum.apply(map("friendsAlbum").map))
}

case class AlbumsSearchMatch(albumsSearch: AlbumsSearch, friendsAlbum: FriendsAlbum) extends ToMap {
  def toMap = Map("albumsSearch" -> albumsSearch, "friendsAlbum" -> friendsAlbum)
}
