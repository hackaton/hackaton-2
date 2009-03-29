package hackaton2.api

trait FromMap[T] {
  class TypedAny(what:Any) {
    def string = what.toString
    def int = what.toString.toInt
    def map = what.asInstanceOf[Map[String,Any]] 
  }

  implicit def any2Typed(what:Any) = new TypedAny(what)

  def apply(map: Map[String, Any]): T
}
