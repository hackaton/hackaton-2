package hackaton2.server.rest

import api.domain._
import domain._

import javax.ws.rs._
import javax.ws.rs.core.Response

@Path("/albums-searches")
@Produces(Array("application/json"))
class AlbumsSearchesResource {

  @POST
  def post(data:Map[String,Any]): Response = {
    try {
      AlbumsSearches ! AlbumsSearch(data)
    } catch {
      case e => {
        e.printStackTrace
        Response.serverError.build
      }
    }
    Response.ok.build
  }

}
