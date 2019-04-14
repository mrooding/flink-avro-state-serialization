package nl.mrooding.data

import java.time.Instant

import nl.mrooding.state.ProductSerializer
import org.apache.avro.generic.{GenericData, GenericRecord}
import org.apache.flink.api.common.typeutils.TypeSerializer

case class ProductOld(
                       id: String,
                       description: Option[String],
                       stock: Option[Long],
                       updatedAt: Instant
                     ) extends AvroGenericRecordWriter {

  def toGenericRecord: GenericRecord = {
    val genericRecord = new GenericData.Record(ProductOld.getCurrentSchema)
    genericRecord.put("id", id)
    genericRecord.put("description", description.orNull)
    genericRecord.put("stock", stock.getOrElse(0l))
    genericRecord.put("updatedAt", updatedAt.toEpochMilli)

    genericRecord
  }
}

object ProductOld extends AvroSchema with AvroSerializable[ProductOld] {
  val schemaPath: String = "/avro/product.avsc"

  val serializer: TypeSerializer[ProductOld] = new ProductSerializer(None)

  def apply(record: GenericRecord): ProductOld = {
    ProductOld(
      id = record.get("id").toString,
      description = Option(record.get("description")).map(_.toString),
      stock = Option(record.get("stock")).map(_.asInstanceOf[Long]),
      updatedAt = Instant.ofEpochMilli(record.get("updatedAt").asInstanceOf[Long])
    )
  }
}
