package hackaton2.api

import com.jteigen.scalatest.JUnit4Runner
import org.junit.runner.RunWith
import org.scalatest.FunSuite

@RunWith(classOf[JUnit4Runner])
class FromJSONTest extends FunSuite {
  test("deserializes json"){
    val json = FromJSON.map("""{"foo":"bar","monkey":"donkey"}""")
    assert(json === Map("foo" -> "bar", "monkey" -> "donkey"))
  }
}