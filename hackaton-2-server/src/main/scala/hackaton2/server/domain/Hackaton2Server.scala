package hackaton2.server.domain

import transmission.client.{DefaultTransmission, Transmission}

/**
 * @author <a href="mailto:trygve.laugstol@arktekk.no">Trygve Laugst&oslash;l</a>
 * @version $Id$
 */
object Hackaton2Server {
  def transmission(): Transmission = new DefaultTransmission("http://localhost:9091/transmission/rpc")
}
