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
        AlbumsSearches ! MatchSearches(a)
        loop(a :: albums)
      }
      case ma: MatchAlbums => {
        albums.foreach((a: Album) => if (a matches ma.albumsSearch.criteria) {
          Http(ma.albumsSearch.origin.url)("albums-matches").post[String,String](ToJSON(FriendsAlbum(MySelf, a)))
        })
        loop(albums)
      }
    }
  }

  start
}

case class MatchAlbums(albumsSearch: AlbumsSearch)