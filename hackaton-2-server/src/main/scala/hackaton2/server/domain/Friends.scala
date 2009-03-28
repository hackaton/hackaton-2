package hackaton2.server.domain

import actors.Actor
import actors.Actor._
import api.{FromMap, ToMap}

object Friends extends Actor {

  def act = loop(Nil)

  def loop(implicit friends:List[Friend]){
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
        friends.foreach (friend => {
          
        })
      }
    }
  }

  def newId(implicit friends:List[Friend]) = (0 /: friends)(_ max _.id) + 1

  start
}

object NewFriend extends FromMap[NewFriend]{
  def apply(map:Map[String,Any]) = NewFriend(map.string("url"), map.string("nick"))
}

case class NewFriend(url:String, nick:String) extends ToMap {
  def toMap = Map("url" -> url, "nick" -> nick)
}

case object ListFriends

case class PostAlbumFriends(friendsAlbum: FriendsAlbum)
