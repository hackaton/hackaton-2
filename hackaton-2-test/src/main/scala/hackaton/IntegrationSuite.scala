package hackaton2.server


import api.{ToJSON, ToMap}
import http.Http
import http.Http._
import java.io.InputStream
import java.net.{URL, HttpURLConnection}
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.{BeforeAndAfter, Spec}
import scala.Option

trait IntegrationSuite extends Spec with BeforeAndAfter with ShouldMatchers with IOUtil {

  var start:Option[Int] = Some(9090)

  override def beforeEach {
    start.foreach(Server.start_!(_))
  }

  override def afterEach {
    start.foreach(_ => Server.stop_!)
  }

  def service = Http("http://localhost:" + start.getOrElse(8080) +"/")

  def post[A <% ToMap](where:String, data:A) = {
    service(where).post[String,String](ToJSON(data.toMap))
  }

  def get(where:String) = service(where).get[String]
}