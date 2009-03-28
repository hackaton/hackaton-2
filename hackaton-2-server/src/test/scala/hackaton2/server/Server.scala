package hackaton2.server

import org.mortbay.jetty.nio.SelectChannelConnector
import org.mortbay.jetty.webapp.WebAppContext
import org.mortbay.jetty.{Server => JettyServer}

object Server extends Application {
  val server = new JettyServer
  val connector = new SelectChannelConnector
  connector.setPort(8080)
  server.setConnectors(Array(connector))

  val context = new WebAppContext
  context.setContextPath("/")
  context.setWar("src/main/webapp")
  server.addHandler(context)

  server.start()
}