package data

import java.time.Instant

import state.ProductSerializer
import org.apache.avro.generic.{GenericData, GenericRecord}
import org.apache.flink.api.common.typeutils.TypeSerializer

case class Product(
                       id: String,
                       description: Option[String],
                       stock: Option[Long],
                       updatedAt: Instant
                     ) extends AvroGenericRecordWriter {

  def toGenericRecord: GenericRecord = {
    val genericRecord = new GenericData.Record(Product.getCurrentSchema)
    genericRecord.put("id", id)
    genericRecord.put("description", description.orNull)
    genericRecord.put("stock", stock.getOrElse(0l))
    genericRecord.put("updatedAt", updatedAt.toEpochMilli)

    genericRecord
  }
}

object Product extends AvroSchema with AvroSerializable[Product] {
  val schemaPath: String = "/avro/product.avsc"

  val serializer: TypeSerializer[Product] = new ProductSerializer(None)

  def apply(record: GenericRecord): Product = {
    Product(
      id = record.get("id").toString,
      description = Option(record.get("description")).map(_.toString),
      stock = Option(record.get("stock")).map(_.asInstanceOf[Long]),
      updatedAt = Instant.ofEpochMilli(record.get("updatedAt").asInstanceOf[Long])
    )
  }
}
