package nl.mrooding.data

import org.apache.flink.api.common.typeutils.TypeSerializer

trait AvroSerializable[T] {
  def serializer: TypeSerializer[T]
}
