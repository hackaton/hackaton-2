package hackaton2.server.domain

import actors.Actor
import actors.Actor._
import api.{FromMap, ToMap, ToJSON}
import http.Http
import http.Http._

object Friends extends Actor {

  def act = loop(Nil)

  def loop(implicit friends:List[Friend]) {
    react {
      case NewFriend(url, nick) => {
        val newFriend = Friend(newId, url, nick)
        reply(newFriend)
        loop(newFriend :: friends)
      }
      case ListFriends => {
        reply(friends)
        loop(friends)
      }
      case p: PostAlbumFriends => {
        val replies = friends.map((friend: Friend) => {
          Http(friend.url)("friends-albums").post[String,(Int, String)](ToJSON(p.friendsAlbum.toMap))._1
        })
        reply(replies)
        loop(friends)
      }
    }
  }

  def newId(implicit friends:List[Friend]) = (0 /: friends)(_ max _.id) + 1

  start
}

object NewFriend extends FromMap[NewFriend]{
  def apply(map:Map[String,Any]) = {
    NewFriend(map("url").toString, map("nick").toString)
  }
}

case class NewFriend(url:String, nick:String) extends ToMap {
  def toMap = Map("url" -> url, "nick" -> nick)
}

case object ListFriends

case class PostAlbumFriends(friendsAlbum: FriendsAlbum)

case class PostAlbumsSearch(albumsSearch: AlbumsSearch)
