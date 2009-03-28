package hackaton2.server.rest

import _root_.java.io._
import _root_.java.lang.annotation.Annotation
import _root_.java.lang.reflect.Type
import javax.ws.rs._
import javax.ws.rs.core._
import javax.ws.rs.ext._

@Produces(Array("application/json"))
@Provider
class JSONProvider extends MessageBodyWriter[Any] {
  def isWriteable(c: Class[_], gt: Type, annotations: Array[Annotation], mediaType: MediaType) =
    classOf[Seq[_]].isAssignableFrom(c) || classOf[Map[_, _]].isAssignableFrom(c)

  def writeTo(t: Any,
             c: Class[_],
             gt: Type,
             annotations: Array[Annotation],
             mediaType: MediaType,
             httpHeaders: MultivaluedMap[String, Object],
             entityStream: OutputStream): Unit = {
    toJSON(t).getBytes.foreach(entityStream.write(_))
  }

  def getSize(t: Any, c: Class[_], gt: Type, annotations: Array[Annotation], mediaType: MediaType) = -1L

  def toJSON(any: Any): String = any match {
    case m: Map[_, _] => m.map(kv => "\"" + kv._1 + "\":" + toJSON(kv._2)).mkString("{", ",", "}")
    case s: Seq[_] => s.map(x => toJSON(x)).mkString("[", ",", "]")
    case x @ (_: Boolean | _:Int | _:Long | _:Double | _:Float) => x.toString
    case null => "null"
    case x => "\"" + x.toString + "\""
  }
}
