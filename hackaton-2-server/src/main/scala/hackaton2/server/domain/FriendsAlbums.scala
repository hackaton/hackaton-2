package hackaton2.server.domain

import actors.Actor
import actors.Actor._

import api.{ToMap, FromMap}

object FriendsAlbums extends Actor {

  def act = loop(Nil)
  
  def loop(friendsAlbums: List[FriendsAlbum]) {
    react {
      case album: FriendsAlbum =>
        val drop = (friendsAlbums.size - 9) max 0
        loop(album :: friendsAlbums.dropRight(drop))
    }
  }
  
  start
}
