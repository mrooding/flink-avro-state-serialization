package state

import data.Product
import org.apache.avro.Schema
import org.apache.flink.api.common.typeutils.TypeSerializer

class ProductSerializerSnapshot(var stateSchema: Option[Schema]) extends CustomAvroSerializerSnapshot[Product] {
  def this() = {
    this(null)
  }

  override def getCurrentSchema: Schema = Product.getCurrentSchema

  override def restoreSerializer(): TypeSerializer[Product] = new ProductSerializer(stateSchema)
}
