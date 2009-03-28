package hackaton2.server.rest

import domain._
import javax.ws.rs._

@Path("/friendsAlbums")
@Produces(Array("application/json"))
class AlbumsResource {

  @POST
  def post(data:Map[String,Any]) = FriendsAlbums !? FriendsAlbum(data)

}
