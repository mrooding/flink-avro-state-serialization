package state

import org.apache.avro.Schema
import org.apache.flink.api.common.typeutils.{TypeSerializer, TypeSerializerSchemaCompatibility, TypeSerializerSnapshot}
import org.apache.flink.core.memory.{DataInputView, DataOutputView}

trait CustomAvroSerializerSnapshot[T] extends TypeSerializerSnapshot[T] {
  var stateSchema: Option[Schema]
  def getCurrentSchema: Schema

  override def getCurrentVersion: Int = 1

  override def writeSnapshot(out: DataOutputView): Unit = out.writeUTF(getCurrentSchema.toString(false))

  override def readSnapshot(readVersion: Int, in: DataInputView, userCodeClassLoader: ClassLoader): Unit = {
    val previousSchemaDefinition = in.readUTF

    this.stateSchema = Some(parseAvroSchema(previousSchemaDefinition))
  }

  private def parseAvroSchema(previousSchemaDefinition: String): Schema = {
    new Schema.Parser().parse(previousSchemaDefinition)
  }

  override def resolveSchemaCompatibility(newSerializer: TypeSerializer[T]): TypeSerializerSchemaCompatibility[T] =
    TypeSerializerSchemaCompatibility.compatibleAsIs()
}
