package state

import data.Product
import org.apache.avro.Schema
import org.apache.avro.generic.GenericRecord
import org.apache.flink.api.common.typeutils.{TypeSerializer, TypeSerializerSnapshot}
import org.apache.flink.util.InstantiationUtil

class ProductSerializer(val stateSchema: Option[Schema]) extends CustomAvroSerializer[Product] {

  override def getCurrentSchema: Schema = Product.getCurrentSchema

  override def fromGenericRecord(genericRecord: GenericRecord): Product = Product.apply(genericRecord)

  override def duplicate(): TypeSerializer[Product] =
    new ProductSerializer(stateSchema)

  override def createInstance(): Product = InstantiationUtil.instantiate(classOf[Product])

  override def snapshotConfiguration(): TypeSerializerSnapshot[Product] = new ProductSerializerSnapshot()
}
