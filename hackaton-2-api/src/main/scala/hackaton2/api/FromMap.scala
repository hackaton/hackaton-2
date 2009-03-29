package hackaton2.api

trait FromMap[T] {
  class TypedMap(map: Map[String, Any]) {
    def string(k: String) = map(k).toString

    def int(k: String) = map(k).toString.toInt
    
    def asMap(k: String) = map(k).asInstanceOf: Map[String, Any]
  }

  implicit def map2typed(map: Map[String, Any]) = new TypedMap(map)

  def apply(map: Map[String, Any]): T
}
