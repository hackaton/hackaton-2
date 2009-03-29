package hackaton2.server.rest


import javax.ws.rs._
import service.AtomFeedProducer

@Path("/feed")
@Produces(Array("application/atom+xml"))
class AtomFeedResource {

  @GET
  def get = (new AtomFeedProducer).createAlbums

}
