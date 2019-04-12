package data

import org.apache.avro.generic.GenericRecord

trait AvroGenericRecordWriter {
  def toGenericRecord: GenericRecord
}
