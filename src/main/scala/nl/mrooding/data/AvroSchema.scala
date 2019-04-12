package nl.mrooding.data

import org.apache.avro.Schema

import scala.io.Source

trait AvroSchema {
  def schemaPath: String

  lazy val getCurrentSchema: Schema = {
    val content = Source.fromURL(getClass.getResource(schemaPath)).mkString

    new Schema.Parser().parse(content)
  }
}
