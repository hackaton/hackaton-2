package hackaton2.server

import java.io.InputStream

trait IOUtil {
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