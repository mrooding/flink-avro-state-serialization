package nl.mrooding.source

import java.time.Instant

import nl.mrooding.data.ProductStock
import org.apache.flink.streaming.api.functions.source.SourceFunction

class ProductStockSource(intervalMs: Long) extends SourceFunction[ProductStock] with Serializable {
  private var isRunning: Boolean = true

  private val r = new scala.util.Random

  override def run(ctx: SourceFunction.SourceContext[ProductStock]): Unit = {
    while (isRunning) {
      ctx.markAsTemporarilyIdle()
      Thread.sleep(intervalMs)
      ctx.collect(ProductStock(random(5).toString, random(10000), Instant.now))
    }
  }

  private def random(max: Int) = {
    0 + r.nextInt(max)
  }

  override def cancel(): Unit = isRunning = false
}

