package hackaton2.server.rest

import domain._
import javax.ws.rs._
import javax.ws.rs.core.Response

@Path("/friends-albums")
@Produces(Array("application/json"))
class FriendsAlbumsResource {

  @POST
  def post(data:Map[String,Any]): Response = {
    val ret = FriendsAlbums ! FriendsAlbum(data)
    Response.ok.build
  }

}
