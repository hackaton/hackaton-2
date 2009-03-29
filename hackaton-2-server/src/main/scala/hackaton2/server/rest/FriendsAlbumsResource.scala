package hackaton2.server.rest

import domain._
import javax.ws.rs._
import javax.ws.rs.core.Response

@Path("/friends-albums")
@Produces(Array("application/json"))
class FriendsAlbumsResource {

  @POST
  def post(data:Map[String,Any]): Response = {
    try {
      FriendsAlbums ! FriendsAlbum(data)
    } catch {
      case e => {
        e.printStackTrace
        Response.serverError.build
      }
    }
    Response.ok.build
  }

}
