package hackaton2.server

import java.io.InputStream
import java.net.{URL, HttpURLConnection}
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.{BeforeAndAfter, Spec}
import rest.{ToJSON, ToMap}

trait IntegrationSuite extends Spec with BeforeAndAfter with ShouldMatchers with IOUtil {
  override def beforeEach {
    Server.start_!(9090)
  }

  override def afterEach {
    Server.stop_!
  }

  implicit def toMap2Map(map: Map[String, Any]) = new ToMap {def toMap = map}

  def get(where: String) = service(where, "GET", None)

  def post[A <% ToMap](where: String, what: A) = {
    service(where, "POST", Some(what.toMap))
  }

  def service(where: String, method: String, body: Option[Map[String, Any]]) = {
    val http = new URL("http://localhost:9090/" + where).openConnection.asInstanceOf[HttpURLConnection]
    http.setRequestMethod(method)
    body.foreach(_ => http.setDoOutput(true))
    http.connect
    body.foreach {
      i =>
              val out = http.getOutputStream
              ToJSON(i).toCharArray.foreach(out.write(_))
    }
    val response = http.getResponseCode
    println(response)
    val in: InputStream = http.getInputStream
    val result = ("" /: in)(_ + _.asInstanceOf[Char])
    in.close
    result
  }
}