package br.com.kafka.ecsabreconta.gateway.output.kafka

import br.com.gabriel.usuario.EventoCriaUsuario
import br.com.kafka.ecsabreconta.core.doman.User
import br.com.kafka.ecsabreconta.core.mapper.UserMapper
import br.com.kafka.ecsabreconta.core.mapper.UserMapper.Companion.toMapper
import org.apache.kafka.common.KafkaException
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service


@Service
class Producer(
    private val kafkaTemplate: KafkaTemplate<String, EventoCriaUsuario>,
    private val userMapper: UserMapper
) {

    fun senMensagem(topic: String, user: User) {
        runCatching {
            val message = userMapper.buildMessage(user.toMapper(), topic)
            kafkaTemplate.send(message)
        }.onFailure {
            throw KafkaException(it)
        }
    }
}