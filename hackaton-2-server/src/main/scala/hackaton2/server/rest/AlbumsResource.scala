package hackaton2.server.rest

import api.domain._
import domain._

import javax.ws.rs._
import javax.ws.rs.core.Response

@Path("/albums")
@Produces(Array("application/json"))
class AlbumsResource {
  
  @POST
  def post(data:Map[String,Any]): Response = {
    try {
      Albums ! Album(data)
    } catch {
      case e => {
        e.printStackTrace
        Response.serverError.build
      }
    }
    Response.ok.build
  }

}
