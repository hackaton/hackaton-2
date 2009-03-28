package hackaton.api


import com.jteigen.scalatest.JUnit4Runner
import hackaton2.api.ToJSON
import org.junit.runner.RunWith
import org.scalatest.FunSuite
import util.parsing.json.JSON

@RunWith(classOf[JUnit4Runner])
class ToJSONTest extends FunSuite {
  test("serializes to and from json"){
    val from = Map("1" -> 1, "2" -> "2", "3" -> List("1", "2", "3"), "4" -> Map("x" -> 1, "y" -> 2))
    assert(JSON.parseFull(ToJSON(from)).get == from)
  }
}