package hackaton2.server

import org.mortbay.jetty.nio.SelectChannelConnector
import org.mortbay.jetty.webapp.WebAppContext
import org.mortbay.jetty.{Server => JettyServer}

object Server {
  val server = new JettyServer
  val connector = new SelectChannelConnector


  def main(args: Array[String]) {
    start_!(8080)
  }

  def start_!(port:Int) {
    connector.setPort(port)
    server.setConnectors(Array(connector))

    val context = new WebAppContext
    context.setContextPath("/")
    context.setWar("src/main/webapp")
    server.addHandler(context)
    server.start
  }

  def stop_! {
    server.stop
  }
}