package hackaton2.server.rest

import javax.ws.rs._

@Path("/friends")
@Produces(Array("application/json"))
class FriendsResource {

  @GET
  def get = Map("a" -> List("A", "B"), "b" -> 5, "c" -> false, "d" -> Map("q" -> "p", "p" -> "q"))

  @POST
  @Path("{id}")
  def post(@PathParam("id") id:Int) = {
    Map("ok" -> true, "updated" -> id)
  }
}