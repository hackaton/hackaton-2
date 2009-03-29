package hackaton2.api

import scala.util.parsing.combinator._

object ToJSON {
  def apply(any: Any):String = any match {
    case t: ToMap => ToJSON(t.toMap)
    case m: Map[_, _] => m.map(kv => "\"" + kv._1 + "\"" + ":" + ToJSON(kv._2)).mkString("{", ",", "}")
    case s: String => "\"" + s + "\""
    case s: Seq[_] => s.map(x => ToJSON(x)).mkString("[", ",", "]")
    case x@(_: Boolean | _: Int | _: Long | _: Double | _: Float) => x.toString
    case null => "null"
  }

  def support(c: Class[_]) =
    classOf[Map[String, Any]].isAssignableFrom(c) ||
            classOf[Seq[Any]].isAssignableFrom(c) ||
            classOf[ToMap].isAssignableFrom(c)

  implicit def toJSON(any:Any) = new {
    def json = ToJSON(any)
  }
}

object FromJSON {
  import scala.util.parsing.combinator._
  class Parser extends JavaTokenParsers {
    def obj: Parser[Map[String, Any]] =
      "{" ~> repsep(member, ",") <~ "}" ^^ (Map() ++ _)

    def arr: Parser[List[Any]] =
      "[" ~> repsep(value, ",") <~ "]"

    def string: Parser[String] = stringLiteral ^^ {case s => s.substring(1, s.length - 1)}

    def member: Parser[(String, Any)] =
      string ~ ":" ~ value ^^
              {case name ~ ":" ~ value => (name, value)}

    def value: Parser[Any] = (obj | arr | string | wholeNumber ^^ (_.toInt) | floatingPointNumber ^^ (_.toDouble) | "null" ^^ (x => null) | "true" ^^ (x => true) | "false" ^^ (x => false))
  }

  object JSON extends Parser {
    def parse(code: String): Any = parseAll(value, code) match {
      case Success(x, _) => x
      case Failure(f, _) => error(f + code)
      case Error(f, _) => error(f)
    }
  }

  def map(s: String) = JSON.parse(s).asInstanceOf[Map[String, Any]]

  def list(s: String) = JSON.parse(s).asInstanceOf[List[Any]]
}
