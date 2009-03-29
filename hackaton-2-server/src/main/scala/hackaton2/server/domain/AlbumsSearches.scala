package hackaton2.server.domain

import api.domain._
import domain._
import api.ToJSON
import http.Http
import http.Http._

import scala.actors.Actor
import scala.actors.Actor._

object AlbumsSearches extends Actor {

  def act() = loop(Nil)
  
  def loop(albumsSearches: List[AlbumsSearch]) {
    react {
      case a: AlbumsSearch => {
        Albums ! MatchAlbums(a)
        loop(a :: albumsSearches)
      }
      case mSearches: MatchSearches => {
        for (s <- albumsSearches; if mSearches.album matches s.criteria) {
          Http(s.origin.url)("albums-matches").post[String,String](ToJSON(FriendsAlbum(MySelf, mSearches.album)))
        }
        loop(albumsSearches)
      }
    }
  }
  
  start
}

case class MatchSearches(album: Album)