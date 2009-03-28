package hackaton2.server.rest


import domain.download.{CurrentDownloads, Downloads}
import javax.ws.rs.{GET, Path, Produces}

/**
 * @author <a href="mailto:trygve.laugstol@arktekk.no">Trygve Laugst&oslash;l</a>
 * @version $Id$
 */
@Path("/download/current")
@Produces(Array("application/json"))
class DownloadResource {

  @GET
  def get() = Downloads !? CurrentDownloads
}
