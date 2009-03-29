package hackaton2.server.domain

import scala.actors.Actor
import scala.actors.Actor._

object AlbumsSearchMatches extends Actor {

  def act = loop(Nil)
  
  def loop(albumsSearchMatches: List[AlbumsSearchMatch]) {
    react {
      case albumsSearchMatch: AlbumsSearchMatch =>
        loop(albumsSearchMatch :: albumsSearchMatches)
    }
  }
  
  start
}
