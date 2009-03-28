package hackaton2.api

object ToJSON {
  def apply(any:Any): String = any match {
    case t: ToMap => ToJSON(t.toMap)
    case m: Map[_, _] => m.map(kv => "\"" + kv._1 + "\":" + ToJSON(kv._2)).mkString("{", ",", "}")
    case s: Seq[_] => s.map(x => ToJSON(x)).mkString("[", ",", "]")
    case x@(_: Boolean | _: Int | _: Long | _: Double | _: Float) => x.toString
    case null => "null"
    case x => "\"" + x.toString + "\""
  }
}
