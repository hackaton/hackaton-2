package hackaton2.api.domain

import com.jteigen.scalatest.JUnit4Runner

import java.io.File
import org.junit.runner.RunWith
import org.scalatest.FunSuite

@RunWith(classOf[JUnit4Runner])
class AlbumTest extends FunSuite {

  test("matching albums") {
    val file = new File("")
    val album = Album("Jokke", "Øl", Song("Jokke", "Øl", 1, "Fylla har skylda", file) :: Song("Jokke", "Øl", 1, "Full mann, full lønn", file) :: Nil, file)
    assert(album matches "øl")
    assert(!(album matches "gå i kirken"))
    assert(album matches "full mann")
  }
  
}
