package hackaton2.api

trait FromMap[T] {
  class TypedAny(what:Any) {
    def string = what.toString
    def int = what.toString.toInt
    def map = what.asInstanceOf[Map[String,Any]] 
  }

  implicit def any2Typed(what:Any) = new TypedAny(what)

  object dynamic {
    implicit def any2Int(what:Any) = any2Typed(what).int
    implicit def any2String(what:Any) = any2Typed(what).string
    implicit def any2Map(what:Any) = any2Typed(what).map
  }

  def apply(map: Map[String, Any]): T
}
