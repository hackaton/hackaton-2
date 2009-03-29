package hackaton2.http


import java.io.{BufferedWriter, OutputStreamWriter, PrintWriter, OutputStream, InputStream}
import Http.properties._
import java.net.{HttpURLConnection, URLEncoder}

object Http {
  import implicits._

  implicit val sendNothing: Send[Nothing] = Send[Nothing]((a, out) => None)
  implicit val sendTuples: Send[(String, String)] = Send[(String, String)]((a, out) => {
    import URLEncoder._
    val writer = new PrintWriter(out)
    writer.println(a.map(param => encode(param._1) + "=" + encode(param._2)).mkString("&"))
    writer.flush()
    writer.close()
  })
  implicit val sendStrings = Send[String]((a, out) => {
    val writer = new PrintWriter(out)
    a.foreach(x => writer.println(x))
    writer.flush()
    writer.close()
  })
  implicit val receiveString: Receive[String] = Receive{ (status,in) => val r = ("" /: in)(_ + _.asInstanceOf[Char]); in.close(); r }
  implicit val receiveInputStream = Receive((status, x) => x)
  implicit val receiveStatusCodeAndString: Receive[(Int, String)] = Receive{ (status, in) => val r = ("" /: in)(_ + _.asInstanceOf[Char]); in.close(); (status, r) }

  implicit val noProperties: Seq[Property] = Nil

  def apply[A, B](url: String, properties: Property*) = new Http(url, properties: _*)

  object properties {
    type Property = HttpURLConnection => Unit

    object Do {
      def apply[A](request: HttpURLConnection => A => Unit) =
        (a: A) => (connection: HttpURLConnection) => request(connection)(a)
    }

    object Do2 {
      def apply[A, B](request: HttpURLConnection => (A, B) => Unit) =
        (a: A, b: B) => (connection: HttpURLConnection) => request(connection)(a, b)
    }

    /* HttpURLConnection */
    val RequestMethod = Do(_.setRequestMethod _)
    val ChunkedStreamingMode = Do(_.setChunkedStreamingMode _)
    val FixedLengthStreamingMode = Do(_.setFixedLengthStreamingMode _)
    val FollowRedirects = Do(_.setInstanceFollowRedirects _)

    /* URLConnection */
    val AddRequestProperty = Do2(_.addRequestProperty _)
    val SetRequestProperty = Do2(_.setRequestProperty _)
    val AllowUserInteraction = Do(_.setAllowUserInteraction _)
    val ConnectTimeout = Do(_.setConnectTimeout _)
    val DefaultUseCaches = Do(_.setDefaultUseCaches _)
    val DoInput = Do(_.setDoInput _)
    val DoOutput = Do(_.setDoOutput _)
    val IfModifiedSince = Do(_.setIfModifiedSince _)
    val ReadTimeout = Do(_.setReadTimeout _)
    val UseCaches = Do(_.setUseCaches _)

    /* convenience */
    val Get = RequestMethod("GET")
    val Post = RequestMethod("POST")
    val Put = RequestMethod("PUT")
    val Delete = RequestMethod("DELETE")
    val ContentType = AddRequestProperty("Content-Type", _:String)
  }

  object implicits {
    implicit def inputStream2Iterator(in: InputStream): Iterator[Int] = new IterableInputStream(in)
  }
}

class Http(url: String, properties: Property*) {

  def apply(path:String, newProps: Property*) = new Http(concatPath(url, path), properties ++ newProps: _*)
  private [Http] def concatPath(url:String, path:String) = {
    if(path == "")
      url
    else{
      val trimmedPath = if(path.startsWith("/")) path.substring(1) else path
      val separator = if(url.endsWith("/")) "" else "/"
      (url + separator + trimmedPath)
    }
  }

  def get[B](implicit receive: Receive[B]) = sendNothing(receive, Get)

  def post[A, B](a: A*)(implicit send: Send[A], receive: Receive[B]) =
    sendAndReceive(a, send, receive, Post)
  def post[B](implicit receive: Receive[B]) = sendNothing(receive, Post)

  def put[A, B](a: A*)(implicit send: Send[A], receive: Receive[B]) =
    sendAndReceive(a, send, receive, Put)
  def put[B](implicit receive: Receive[B]) = sendNothing(receive, Put)

  def delete[A, B](a: A*)(implicit send: Send[A], receive: Receive[B]) =
    sendAndReceive(a, send, receive, Delete)
  def delete[B](implicit receive: Receive[B]) = sendNothing(receive, Delete)

  private [this] def sendNothing[B](receive: Receive[B], property: Property) =
    process(None, properties ++ List(property): _*)(Http.sendNothing, receive)
  private [this] def sendAndReceive[A, B](a: Seq[A], send: Send[A], receive: Receive[B], property: Property) =
    process(Some(a), properties ++ List(property): _*)(send, receive)

  def process[A, B](a: Option[Seq[A]], properties: Property*)(implicit send: Send[A], receive: Receive[B]) = {
    val connection = new java.net.URL(url).openConnection.asInstanceOf[HttpURLConnection]
    properties ++ receive.properties ++ send.properties foreach (x => x(connection))
    //print(">>> "+ connection.getRequestMethod + " | " + url)
    connection.connect()
    a.foreach(x => send(x, connection.getOutputStream))

    val status = connection.getResponseCode
    val in = status match {
      case x if {x >= 100 && x < 400} => connection.getInputStream
      case x if x >= 400 => connection.getErrorStream
    }
    //println(" | " + status)
    receive(status, in)
  }
}

trait Response {
  def errorStream
  def headerField(nth: Int)
  def headerDateField(name:String, default: Long)
  def headerFieldKey(nth: Int)
  def responseCode
  def responseMessage
}

trait Send[A] extends ((Seq[A], OutputStream) => Unit) {
  def properties: Seq[Property] = Nil
}

object Send {
  def apply[A](fn: (Seq[A], OutputStream) => Unit, requiredProperties: Property*) =
    new Send[A] {
      override def properties = requiredProperties ++ List(DoOutput(true))
      def apply(a: Seq[A], out: OutputStream) = fn(a, out)
    }
}

trait Receive[A] extends ((Int, InputStream) => A) {
  def properties: Seq[Property] = Nil
}

object Receive {
  def apply[A](fn: (Int, InputStream) => A, requiredProperties: Property*) =
    new Receive[A] {
      override def properties = requiredProperties ++ List(DoInput(true))
      def apply(status: Int, in: InputStream) = fn(status, in)
    }
}

private[http] class IterableInputStream(in: InputStream) extends Iterator[Int] {
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

