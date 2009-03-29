package hackaton2.server.domain

import scala.actors.Actor
import scala.actors.Actor._

object AlbumsSearchMatches extends Actor {

  def act = loop(Nil)
  
  def loop(albumsSearchMatches: List[AlbumsSearchMatch]) {
    react {
      case albumsSearchMatch: AlbumsSearchMatch =>
        loop(albumsSearchMatch :: albumsSearchMatches)
      case ListAlbumsSearchMatches =>
        reply(albumsSearchMatches)
        loop(albumsSearchMatches)
      case ClearAlbumsSearchMatches =>
        reply(albumsSearchMatches)
        loop(Nil)
    }
  }
  
  def all = (AlbumsSearchMatches !? ListAlbumsSearchMatches).asInstanceOf[List[AlbumsSearchMatch]]
  
  start
}

case object ListAlbumsSearchMatches

case object ClearAlbumsSearchMatches
