package hackaton2.server

import org.mortbay.jetty.nio.SelectChannelConnector
import org.mortbay.jetty.webapp.WebAppContext
import org.mortbay.jetty.{Server => JettyServer}
import scala.collection.mutable.Map

object Server {

  val servers = Map[Int, JettyServer]()

  def start_!(port: Int) {
    val server = new JettyServer
    val connector = new SelectChannelConnector
    connector.setPort(port)
    server.setConnectors(Array(connector))

    val context = new WebAppContext
    context.setContextPath("/")
    context.setWar("src/main/webapp")
    server.addHandler(context)
    server.start
    servers(port) = server
  }                                                                                                   

  def stop_!(port: Int) {
    servers(port).stop
  }
}
