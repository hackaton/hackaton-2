package hackaton

import hackaton2.api.ToJSON
import hackaton2.http.Http
import hackaton2.http.Http._
import java.net.URL
import java.io.File

object Indexer {
  def main(args: Array[String]) {
//    val url = getClass.getResource("/music")
    val music = new File("<path>")
    val fileScanner = new FileScanner(music.getPath)

    val service = Http("http://localhost:8080/") ("albums")
    val body = ToJSON(fileScanner.albums)
    println(body)
    println(service.post[String, String](body))
  }
}
