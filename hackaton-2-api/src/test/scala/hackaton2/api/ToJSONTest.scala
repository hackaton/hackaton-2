package hackaton.api


import com.jteigen.scalatest.JUnit4Runner
import hackaton2.api.{FromJSON, ToJSON}
import org.junit.runner.RunWith
import org.scalatest.FunSuite

@RunWith(classOf[JUnit4Runner])
class ToJSONTest extends FunSuite {
  test("serializes to and from json"){
    val from = Map("1" -> 1, "2" -> "2", "3" -> List("1", "2", "3"), "4" -> Map("x" -> 1, "y" -> 2))
    assert(FromJSON.map(ToJSON(from)) == from)
  }

  test("map serializing"){
    val json = ToJSON(Map("url" -> "http://foo.com", "nick" -> "monkey"))
    assert(json === """{"url":"http://foo.com","nick":"monkey"}""")
  }

  test("handles Nil"){
    assert(ToJSON(Nil) === "[]")
  }
}