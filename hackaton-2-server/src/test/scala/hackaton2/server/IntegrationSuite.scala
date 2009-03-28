package hackaton2.server

import java.io.InputStream
import org.scalatest.matchers.ShouldMatchers
import org.scalatest.{BeforeAndAfter, Spec}

trait IntegrationSuite extends Spec with BeforeAndAfter with ShouldMatchers {
  override def beforeEach {
    Server.start_!(9090)
  }

  override def afterEach {
    Server.stop_!
  }

  class IterableInputStream(in: InputStream) extends Iterator[Int] {
    var _next = -1
    var read = true

    def next = {
      if (hasNext) {
        read = true
        _next
      } else {
        error("end of stream")
      }
    }

    def hasNext = {
      if (read && in != null) {
        _next = in.read()
        read = false
      }
      _next != -1
    }
  }

  implicit def inputStream2Iterable(in:InputStream) = new IterableInputStream(in)
}