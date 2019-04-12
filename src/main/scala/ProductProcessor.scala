import data.{Product, ProductDescription, ProductStock}
import org.apache.flink.api.common.state.{ValueState, ValueStateDescriptor}
import org.apache.flink.streaming.api.functions.co.CoProcessFunction
import org.apache.flink.util.Collector

case class ProductProcessor() extends CoProcessFunction[ProductDescription, ProductStock, Product] {
  private[this] lazy val stateDescriptor: ValueStateDescriptor[Product] =
    new ValueStateDescriptor[Product]("product-join", Product.serializer)
  private[this] lazy val state: ValueState[Product] = getRuntimeContext.getState(stateDescriptor)

  override def processElement1(value: ProductDescription, ctx: CoProcessFunction[ProductDescription, ProductStock, Product]#Context, out: Collector[Product]): Unit = {
    val product = Option(state.value()) match {
      case Some(stateProduct) =>
        stateProduct.copy(
          description = Some(value.description),
          updatedAt = value.updatedAt
        )
      case None =>
        Product(
          id = value.id,
          description = Some(value.description),
          stock = None,
          updatedAt = value.updatedAt)
    }

    state.update(product)
    out.collect(product)
  }

  override def processElement2(value: ProductStock, ctx: CoProcessFunction[ProductDescription, ProductStock, Product]#Context, out: Collector[Product]): Unit = {
    val product = Option(state.value()) match {
      case Some(stateProduct) =>
        stateProduct.copy(
          stock = Some(value.stock),
          updatedAt = value.updatedAt
        )
      case None =>
        Product(
          id = value.id,
          description = None,
          stock = Some(value.stock),
          updatedAt = value.updatedAt)
    }

    state.update(product)
    out.collect(product)
  }
}
