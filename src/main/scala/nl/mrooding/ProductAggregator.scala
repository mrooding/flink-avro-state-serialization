package nl.mrooding

import nl.mrooding.data.{ProductDescription, ProductStock}
import nl.mrooding.source.{ProductDescriptionSource, ProductStockSource}
import org.apache.flink.api.scala._
import org.apache.flink.streaming.api.scala.{DataStream, StreamExecutionEnvironment}

object ProductAggregator {
  private[this] val intervalMs = 1000

  def main(args: Array[String]) : Unit = {
    val env: StreamExecutionEnvironment = StreamExecutionEnvironment.getExecutionEnvironment

    val productDescriptionStream: DataStream[ProductDescription] = env
      .addSource(new ProductDescriptionSource(intervalMs = intervalMs))
      .keyBy(_.id)
    val productStockStream: DataStream[ProductStock] = env
      .addSource(new ProductStockSource(intervalMs = intervalMs))
      .keyBy(_.id)

    productDescriptionStream
      .connect(productStockStream)
      .process(ProductProcessor())
      .print()
      .setParallelism(1)

    env.execute("Product aggregator")
  }
}
