package hackaton2.server


import api.{ToJSON, ToMap}
import java.io.InputStream
import java.net.{URL, HttpURLConnection}
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.{BeforeAndAfter, Spec}

trait IntegrationSuite extends Spec with BeforeAndAfter with ShouldMatchers with IOUtil {

  var start:Option[Int] = Some(9090)

  override def beforeEach {
    start.foreach(Server.start_!(_))
  }

  override def afterEach {
    start.foreach(_ => Server.stop_!)
  }

  implicit def toMap2Map(map: Map[String, Any]) = new ToMap {def toMap = map}

  def get(where: String) = service(where, "GET", None)

  def post[A <% ToMap](where: String, what: A) = {
    service(where, "POST", Some(what.toMap))
  }

  def service(where: String, method: String, body: Option[Map[String, Any]]) = {
    val http = new URL("http://localhost:" + start.getOrElse(8080) + "/" + where).openConnection.asInstanceOf[HttpURLConnection]
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