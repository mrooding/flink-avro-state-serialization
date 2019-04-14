package nl.mrooding.state

import nl.mrooding.data.ProductOld
import org.apache.avro.Schema
import org.apache.flink.api.common.typeutils.TypeSerializer

class ProductSerializerSnapshot(var stateSchema: Option[Schema]) extends CustomAvroSerializerSnapshot[ProductOld] {
  def this() = {
    this(null)
  }

  override def getCurrentSchema: Schema = ProductOld.getCurrentSchema

  override def restoreSerializer(): TypeSerializer[ProductOld] = new ProductSerializer(stateSchema)
}
