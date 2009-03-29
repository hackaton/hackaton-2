package hackaton2.server.rest


import domain.{ListFriends, Friends, NewFriend, Friend}
import javax.ws.rs._

@Path("/friends")
@Produces(Array("application/json"))
class FriendsResource {

  @GET
  @Path("{id}")
  def get(@PathParam("id") id:Int) = List[Friend]()

  @GET
  def list = Friends !? ListFriends

  @POST
  def post(data:Map[String,Any]) = Friends !? NewFriend(data)
}
