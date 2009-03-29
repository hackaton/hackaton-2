package hackaton2.server.domain

import api.domain._
import domain._
import api.ToJSON._
import http.Http
import http.Http._

import scala.actors.Actor
import scala.actors.Actor._

object AlbumsSearches extends Actor {

  def act() = loop(Nil)
  
  def loop(albumsSearches: List[AlbumsSearch]) {
    react {
      case albumsSearch: AlbumsSearch => {
        Albums ! albumsSearch
        loop(albumsSearch :: albumsSearches)
      }
      case album: Album => {
        var replies = albumsSearches.filter(album matches _.criteria).map((albumSearch) => {
          Http(albumSearch.origin.url)("albums-search-matches").post[String,(Int,String)](AlbumsSearchMatch(albumSearch,FriendsAlbum(MySelf, album)).json)._1
        })
        loop(albumsSearches)
      }
    }
  }
  
  start
}
