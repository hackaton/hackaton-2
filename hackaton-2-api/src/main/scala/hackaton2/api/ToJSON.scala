package hackaton2.api


import util.parsing.json.JSON

object ToJSON extends PartialFunction[Any, String] {
  def apply(any:Any) = partial.apply(any)
  def isDefinedAt(any:Any) = partial.isDefinedAt(any)

  private [this] val partial:PartialFunction[Any,String] = (a:Any) => a match {
    case t: ToMap => ToJSON(t.toMap)
    case m: Map[_, _] => m.map(kv => "\"" + kv._1 + "\":" + ToJSON(kv._2)).mkString("{", ",", "}")
    case s: Seq[_] => s.map(x => ToJSON(x)).mkString("[", ",", "]")
    case x@(_: Boolean | _: Int | _: Long | _: Double | _: Float) => x.toString
    case null => "null"
    case x => "\"" + x.toString + "\""
  }
}

object FromJSON {

  JSON.globalNumberParser = {_.toInt}

  def map(s:String) = {
    JSON.parseFull(s).map(_.asInstanceOf[Map[String,Any]]).getOrElse(Map[String,Any]())
  }

  def list(s:String) = {
    JSON.parse(s).getOrElse(Nil)
  }
}
