package br.com.kafka.ecsabreconta.config

import br.com.gabriel.usuario.EventoCriaUsuario
import org.apache.avro.generic.GenericDatumWriter
import org.apache.avro.io.EncoderFactory
import org.apache.kafka.common.serialization.Serializer
import java.io.ByteArrayOutputStream
import java.io.IOException

class AvroMessageSerializer: Serializer<EventoCriaUsuario> {
    override fun serialize(topic: String?, data: EventoCriaUsuario?): ByteArray {
        var arr = ByteArray(100000)
        try {
            ByteArrayOutputStream().use { outputStream ->
                val binaryEncoder = EncoderFactory.get().binaryEncoder(outputStream, null)
                val writer: GenericDatumWriter<EventoCriaUsuario> = GenericDatumWriter<EventoCriaUsuario>(data!!.schema)
                writer.write(data, binaryEncoder)
                binaryEncoder.flush()
                arr = outputStream.toByteArray()
            }
        }catch (e: IOException) {
            e.printStackTrace();
        }
        return arr
    }
}