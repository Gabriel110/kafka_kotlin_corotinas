package br.com.kafka.ecsabreconta.core.mapper

import br.com.gabriel.usuario.EventoCriaUsuario
import br.com.gabriel.usuario.EventoCriarUsuarioData
import br.com.kafka.ecsabreconta.core.doman.User
import java.util.UUID
import org.springframework.kafka.support.KafkaHeaders
import org.springframework.messaging.Message
import org.springframework.messaging.support.MessageBuilder
import org.springframework.stereotype.Component
import java.time.LocalDateTime

@Component
class UserMapper {
    companion object {
        fun User.toMapper(): EventoCriaUsuario {
            val data = EventoCriarUsuarioData.newBuilder()
                .setDataNascimento(this.dataNascimento)
                .setCpf(this.cpf)
                .setNome(this.nome)
                .build()

            return EventoCriaUsuario.newBuilder()
                .setData(data)
                .build()
        }
    }

    fun buildMessage(event: EventoCriaUsuario, topic: String): Message<EventoCriaUsuario> {
        return MessageBuilder.withPayload(event)
            .setHeader(KafkaHeaders.TOPIC, topic)
            .setHeader("transactionid", UUID.randomUUID().toString())
            .setHeader("correlationid", UUID.randomUUID().toString())
            .setHeader("source", "hv5")
            .setHeader("type","br.com.gabriel.usuario")
            .setHeader("eventversion","v1.0")
            .setHeader("specversion","001")
            .setHeader("time", LocalDateTime.now().toString())
            .setHeader("datacontenttype","application/avro")
            .build()
    }
}