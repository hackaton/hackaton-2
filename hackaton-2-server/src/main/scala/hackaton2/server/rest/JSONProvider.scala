package hackaton2.server.rest

import _root_.java.io._
import _root_.java.lang.annotation.Annotation
import _root_.java.lang.reflect.Type
import javax.ws.rs._
import javax.ws.rs.core._
import javax.ws.rs.ext._
import util.parsing.json.JSON

@Produces(Array("application/json"))
@Provider
class JSONWriterProvider extends MessageBodyWriter[Any] {
  def isWriteable(c: Class[_], gt: Type, annotations: Array[Annotation], mediaType: MediaType) =
    classOf[Seq[_]].isAssignableFrom(c) || classOf[Map[_, _]].isAssignableFrom(c)

  def writeTo(t: Any,
             c: Class[_],
             gt: Type,
             annotations: Array[Annotation],
             mediaType: MediaType,
             httpHeaders: MultivaluedMap[String, Object],
             entityStream: OutputStream): Unit = {
    ToJSON(t).getBytes.foreach(entityStream.write(_))
  }

  def getSize(t: Any, c: Class[_], gt: Type, annotations: Array[Annotation], mediaType: MediaType) = -1L
}

object ToJSON {
  def apply(any:Any): String = any match {
    case t: ToMap => ToJSON(t.toMap)
    case m: Map[_, _] => m.map(kv => "\"" + kv._1 + "\":" + ToJSON(kv._2)).mkString("{", ",", "}")
    case s: Seq[_] => s.map(x => ToJSON(x)).mkString("[", ",", "]")
    case x@(_: Boolean | _: Int | _: Long | _: Double | _: Float) => x.toString
    case null => "null"
    case x => "\"" + x.toString + "\""
  }
}

@Provider
class JSONReaderProvider extends MessageBodyReader[Map[String, Any]] with IOUtil {
  def readFrom(clazz: Class[Map[String, Any]],
              t: Type,
              annotations: Array[Annotation],
              mediaType: MediaType,
              multiValue: MultivaluedMap[String, String],
              in: InputStream) =
    JSON.parseFull(("" /: in)(_ + _.asInstanceOf[Char])).asInstanceOf[Map[String, Any]]

  def isReadable(clazz: Class[_],
                t: Type,
                annotation: Array[Annotation],
                mediaType: MediaType) = clazz.isAssignableFrom(classOf[Map[String, Any]])
}

trait FromMap[T] {
  class TypedMap(map: Map[String, Any]) {
    def string(k: String) = map(k).toString

    def int(k: String) = map(k).toString.toInt
  }

  implicit def map2typed(map: Map[String, Any]) = new TypedMap(map)

  def apply(map: Map[String, Any]): T
}

trait ToMap {
  def toMap: Map[String, Any]
}
