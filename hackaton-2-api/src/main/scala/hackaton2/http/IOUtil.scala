package hackaton2.server


import java.io.{BufferedReader, Reader, InputStream}

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

  class IterableReader(in:BufferedReader) extends Iterator[String] {
    var _next:String = null
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
        _next = in.readLine
        read = false
      }
      _next != null
    }
  }

  implicit def inputStream2Iterable(in:InputStream) = new IterableInputStream(in)
  implicit def bufferedReader2Iterable(in:BufferedReader) = new IterableReader(in)
}