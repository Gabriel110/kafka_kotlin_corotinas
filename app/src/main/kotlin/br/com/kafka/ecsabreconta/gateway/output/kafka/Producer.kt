package br.com.kafka.ecsabreconta.gateway.output.kafka

import br.com.kafka.ecsabreconta.core.doman.User
import org.apache.kafka.clients.producer.ProducerRecord
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.support.SendResult
import org.springframework.stereotype.Service
import java.util.concurrent.CompletableFuture


@Service
class Producer(
    private val kafkaTemplate: KafkaTemplate<String, User>
) {

    fun senMensagem(topic: String, user: User) {
        val future: CompletableFuture<SendResult<String, User>> = kafkaTemplate.send(ProducerRecord(topic, user))

        future.whenComplete { result, exception ->
            if (exception != null) {
                println("Failed to send message: ${exception.message}")
            } else {
                println("Sent message with offset ${result?.recordMetadata?.offset()}")
            }
        }
    }
}