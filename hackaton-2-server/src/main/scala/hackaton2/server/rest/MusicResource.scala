package hackaton2.server.rest


import javax.ws.rs._
import service.FeedConsumer

@Path("/albums")
@Produces(Array("application/atom+xml"))
class MusicResource {

  @GET
  def get = (new FeedConsumer).createAlbums

}
