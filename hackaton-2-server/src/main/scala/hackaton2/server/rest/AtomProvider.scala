package hackaton2.server.rest

import java.io.OutputStream
import java.lang.annotation.Annotation
import java.lang.{String, Class}

import java.lang.reflect.Type
import javax.ws.rs.core.{MediaType, MultivaluedMap}

import javax.ws.rs.ext.{MessageBodyWriter, Provider}
import javax.ws.rs.Produces
import org.apache.abdera.model.{Entry, Element, Document, Feed}
@Produces(Array("application/atom+xml"))
@Provider
class AtomWriterProvider extends MessageBodyWriter[Any]{
  def isWriteable(c: Class[_], gt: Type, annotations: Array[Annotation], mediaType: MediaType) =
    classOf[Feed].isAssignableFrom(c)

  def writeTo(t: Any,
             c: Class[_],
             gt: Type,
             annotations: Array[Annotation],
             mediaType: MediaType,
             httpHeaders: MultivaluedMap[String, Object],
             entityStream: OutputStream) = {
    var document = t.asInstanceOf[Feed]
    document.writeTo(entityStream)
  }

  def getSize(t: Any, c: Class[_], gt: Type, annotations: Array[Annotation], mediaType: MediaType) = -1L  

}