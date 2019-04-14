package nl.mrooding.state

import nl.mrooding.data.ProductOld
import org.apache.avro.Schema
import org.apache.avro.generic.GenericRecord
import org.apache.flink.api.common.typeutils.{TypeSerializer, TypeSerializerSnapshot}
import org.apache.flink.util.InstantiationUtil

class ProductSerializer(val stateSchema: Option[Schema]) extends CustomAvroSerializer[ProductOld] {

  override def getCurrentSchema: Schema = ProductOld.getCurrentSchema

  override def fromGenericRecord(genericRecord: GenericRecord): ProductOld = ProductOld.apply(genericRecord)

  override def duplicate(): TypeSerializer[ProductOld] =
    new ProductSerializer(stateSchema)

  override def createInstance(): ProductOld = InstantiationUtil.instantiate(classOf[ProductOld])

  override def snapshotConfiguration(): TypeSerializerSnapshot[ProductOld] = new ProductSerializerSnapshot()
}
