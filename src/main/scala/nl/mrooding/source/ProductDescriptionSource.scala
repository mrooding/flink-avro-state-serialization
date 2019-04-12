package nl.mrooding.source

import java.time.Instant

import nl.mrooding.data.ProductDescription
import org.apache.flink.streaming.api.functions.source.SourceFunction

class ProductDescriptionSource(intervalMs: Long) extends SourceFunction[ProductDescription] with Serializable {
  private var isRunning: Boolean = true

  private val r = new scala.util.Random

  override def run(ctx: SourceFunction.SourceContext[ProductDescription]): Unit = {
    while (isRunning) {
      ctx.markAsTemporarilyIdle()
      Thread.sleep(intervalMs)
      ctx.collect(ProductDescription(
        random.toString,
        s"Product $random",
        Instant.now
      ))
    }
  }

  private def random = {
    0 + r.nextInt(5)
  }

  override def cancel(): Unit = isRunning = false
}
