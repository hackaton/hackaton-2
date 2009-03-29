package hackaton2.server.domain

import api.domain._
import api.ToJSON
import domain._
import http.Http
import http.Http._

import scala.actors.Actor
import scala.actors.Actor._

object Albums extends Actor {
  
  def act() = loop(Nil)
  
  def loop(albums: List[Album]) {
    react {
      case a: Album => {
        AlbumsSearches ! a
        reply(a)
        loop(a :: albums)
      }
      case search: AlbumsSearch => {
        var replies = albums.filter(_ matches search.criteria).map((album) => {
          Http(search.origin.url)("albums-search-matches").post[String,(Int,String)](ToJSON(AlbumsSearchMatch(search, FriendsAlbum(MySelf, album))))._1
        })
        reply(replies)
        loop(albums)
      }
    }
  }

  start
}
