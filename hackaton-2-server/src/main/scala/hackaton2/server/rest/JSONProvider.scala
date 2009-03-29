package hackaton2.server.rest

import _root_.java.io._
import _root_.java.lang.annotation.Annotation
import _root_.java.lang.reflect.Type
import api.{FromJSON, ToMap, ToJSON}
import javax.ws.rs._
import javax.ws.rs.core._
import javax.ws.rs.ext._
import util.parsing.json.JSON

@Produces(Array("application/json"))
@Provider
class JSONWriterProvider extends MessageBodyWriter[Any] {

  def isWriteable(c: Class[_], gt: Type, annotations: Array[Annotation], mediaType: MediaType) = ToJSON.support(c)

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

@Provider
class JSONReaderMapProvider extends MessageBodyReader[Map[String, Any]] with IOUtil {

  def readFrom(clazz: Class[Map[String, Any]],
              t: Type,
              annotations: Array[Annotation],
              mediaType: MediaType,
              multiValue: MultivaluedMap[String, String],
              in: InputStream): Map[String, Any] = {
    val reader = new BufferedReader(new InputStreamReader(in))
    FromJSON.map(("" /: reader)(_ + _))
  }

  def isReadable(clazz: Class[_],
                t: Type,
                annotation: Array[Annotation],
                mediaType: MediaType) = {
    clazz.isAssignableFrom(classOf[Map[String, Any]])
  }
}

@Provider
class JSONReaderListProvider extends MessageBodyReader[List[Any]] with IOUtil {

  def readFrom(clazz: Class[List[Any]],
              t: Type,
              annotations: Array[Annotation],
              mediaType: MediaType,
              multiValue: MultivaluedMap[String, String],
              in: InputStream): List[Any] = {
    val reader = new BufferedReader(new InputStreamReader(in))
    FromJSON.list(("" /: reader)(_ + _))
  }

  def isReadable(clazz: Class[_],
                t: Type,
                annotation: Array[Annotation],
                mediaType: MediaType) = {
    clazz.isAssignableFrom(classOf[List[Any]])
  }
}