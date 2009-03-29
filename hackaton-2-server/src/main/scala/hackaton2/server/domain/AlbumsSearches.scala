package hackaton2.server.domain

import scala.actors.Actor
import scala.actors.Actor._

object AlbumsSearches extends Actor {

  def act() = loop(Nil)
  
  def loop(albumsSearches: List[AlbumsSearch]) {
    react {
      case a: AlbumsSearch => {
        // searchy thingy
        loop(a :: albumsSearches)
      }
    }
  }
  
}
